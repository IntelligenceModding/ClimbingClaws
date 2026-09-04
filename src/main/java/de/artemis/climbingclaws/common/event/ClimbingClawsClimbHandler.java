package de.artemis.climbingclaws.common.event;

import de.artemis.climbingclaws.common.config.ClimbingClawsConfig;
import de.artemis.climbingclaws.common.network.WallSpringCooldownPayload;
import de.artemis.climbingclaws.common.registry.ModCriteriaTriggers;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import de.artemis.climbingclaws.common.registry.ModStats;
import de.artemis.climbingclaws.compat.curios.CuriosCompat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public final class ClimbingClawsClimbHandler {
    private static final String CURIOS_MOD_ID = "curios";
    private static final int CLIMB_FEEDBACK_INTERVAL_TICKS = 4;
    private static final int CLING_FEEDBACK_INTERVAL_TICKS = 10;
    private static final int WALL_SPRING_BLOCK_PARTICLE_COUNT = 10;
    private static final int WALL_SPRING_CLOUD_PARTICLE_COUNT = 4;
    private static final int CLIMBING_STAT_GRACE_TICKS = 10;
    private static final ResourceKey<Enchantment> EFFICIENCY = net.minecraft.world.item.enchantment.Enchantments.EFFICIENCY;
    private static final Map<UUID, Integer> WALL_SPRING_COOLDOWNS = new HashMap<>();
    private static final Map<UUID, Double> LAST_TRACKED_HEIGHTS = new HashMap<>();
    private static final Map<UUID, Integer> STAT_GRACE_WINDOWS = new HashMap<>();
    private static int clientWallSpringCooldownTicks;

    private ClimbingClawsClimbHandler() {
    }

    public static void tickClientWallSpringCooldown() {
        if (clientWallSpringCooldownTicks > 0) {
            clientWallSpringCooldownTicks--;
        }
    }

    public static void syncClientWallSpringCooldown(int ticks) {
        clientWallSpringCooldownTicks = Math.max(clientWallSpringCooldownTicks, ticks);
    }

    public static void clearClientWallSpringCooldown() {
        clientWallSpringCooldownTicks = 0;
    }

    public static float getClientWallSpringCooldownPercent() {
        if (!ClimbingClawsConfig.enableWallSpring() || clientWallSpringCooldownTicks <= 0) {
            return 0.0F;
        }

        int cooldownTicks = ClimbingClawsConfig.wallSpringCooldownTicks();
        if (cooldownTicks <= 0) {
            return 0.0F;
        }

        return Mth.clamp((float) clientWallSpringCooldownTicks / (float) cooldownTicks, 0.0F, 1.0F);
    }

    public static boolean hasActiveWallSpring(Player player) {
        EquippedClaws equippedClaws = findActiveClaws(player);
        return ClimbingClawsConfig.enableWallSpring()
                && equippedClaws != null
                && getEnchantmentLevel(equippedClaws.stack(), player, ModEnchantments.WALL_SPRING) > 0;
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        tickWallSpringCooldown(player);
        tickStatGraceWindow(player);

        EquippedClaws equippedClaws = findActiveClaws(player);
        Level level = player.level();
        if (!ClimbingClawsConfig.enableClimbing() || player.isSpectator() || player.isPassenger() || equippedClaws == null) {
            clearTrackedHeight(player);
            return;
        }

        ItemStack clawsStack = equippedClaws.stack();
        boolean allowPartialSurfaces = ClimbingClawsConfig.enableCanopyGripEffect()
                && getEnchantmentLevel(clawsStack, player, ModEnchantments.CANOPY_GRIP) > 0;
        SurfaceContact sideSurface = ClimbingClawsConfig.enableWallClimbing()
                ? findHorizontalSurface(level, player.getBoundingBox().inflate(0.08D, 0.0D, 0.08D), allowPartialSurfaces)
                : null;
        SurfaceContact ceilingSurface = ClimbingClawsConfig.enableCeilingClimbing()
                ? findSurface(level, player.getBoundingBox().move(0.0D, 0.12D, 0.0D).inflate(-0.02D, 0.0D, -0.02D), Direction.DOWN, allowPartialSurfaces)
                : null;
        boolean touchingSideSurface = sideSurface != null;
        boolean touchingCeilingSurface = ceilingSurface != null;
        boolean touchingPartialSurface = isPartialSurface(level, sideSurface) || isPartialSurface(level, ceilingSurface);
        if (!touchingSideSurface && !touchingCeilingSurface) {
            trackClimbingDistance(player, false);
            return;
        }

        Vec3 movement = player.getDeltaMovement();
        double horizontalVelocityLimit = ClimbingClawsConfig.horizontalVelocityLimit();
        double x = Mth.clamp(movement.x, -horizontalVelocityLimit, horizontalVelocityLimit);
        double z = Mth.clamp(movement.z, -horizontalVelocityLimit, horizontalVelocityLimit);
        double y = Math.max(movement.y, -ClimbingClawsConfig.fallSpeedLimitWhileAttached());
        boolean movingIntoWall = Math.abs(player.zza) > 0.01F || Math.abs(player.xxa) > 0.01F;
        boolean activeClimb = false;
        boolean activeSideClimb = false;
        boolean activeCeilingClimb = false;
        boolean descending = ClimbingClawsConfig.enableControlledDescent()
                && player.isShiftKeyDown()
                && (touchingSideSurface || touchingCeilingSurface);
        boolean hanging = ClimbingClawsConfig.enableHanging()
                && !player.isShiftKeyDown()
                && !movingIntoWall
                && (touchingSideSurface || touchingCeilingSurface);
        double climbSpeed = getClimbSpeed(clawsStack, player, ClimbingClawsConfig.sideClimbSpeed());
        double ceilingClimbSpeed = getClimbSpeed(clawsStack, player, ClimbingClawsConfig.ceilingClimbSpeed());
        double ceilingHoldSpeed = getClimbSpeed(clawsStack, player, ClimbingClawsConfig.ceilingHoldSpeed());

        if (descending) {
            y = Math.min(y, -climbSpeed);
            activeClimb = true;
        } else {
            if (touchingSideSurface && movingIntoWall) {
                y = Math.max(y, climbSpeed);
                activeClimb = true;
                activeSideClimb = true;
            } else if (touchingSideSurface && hanging) {
                y = Math.max(y, 0.0D);
            }
            if (touchingCeilingSurface) {
                y = Math.max(y, movingIntoWall ? ceilingClimbSpeed : ceilingHoldSpeed);
                activeClimb = true;
                activeCeilingClimb = true;
            }
        }

        boolean usingClawsOnSurface = activeClimb || hanging || descending;
        if (usingClawsOnSurface) {
            player.fallDistance = 0.0F;
        }

        player.setDeltaMovement(x, y, z);
        player.hurtMarked = usingClawsOnSurface;
        awardClimbingStats(player, activeSideClimb, descending, hanging);
        triggerAdvancements(player, activeSideClimb, hanging, activeCeilingClimb, touchingPartialSurface);
        trackClimbingDistance(player, usingClawsOnSurface);

        damageClaws(player, equippedClaws, activeClimb, usingClawsOnSurface, false);

        if (activeClimb && player.tickCount % CLIMB_FEEDBACK_INTERVAL_TICKS == 0) {
            playClimbFeedback(player, level, touchingCeilingSurface ? ceilingSurface : sideSurface, touchingCeilingSurface);
        } else if (!activeClimb
                && hanging
                && touchingSideSurface
                && player.tickCount % CLING_FEEDBACK_INTERVAL_TICKS == 0) {
            playClingFeedback(player, level, sideSurface);
        }
    }

    public static boolean activateBurst(Player player) {
        EquippedClaws equippedClaws = findActiveClaws(player);
        if (!ClimbingClawsConfig.enableClimbing()
                || !ClimbingClawsConfig.enableWallSpring()
                || player.level().isClientSide()
                || player.isSpectator()
                || player.isPassenger()
                || equippedClaws == null
                || (player.isShiftKeyDown() && !ClimbingClawsConfig.allowWallSpringWhileSneaking())) {
            return false;
        }

        Level level = player.level();
        ItemStack clawsStack = equippedClaws.stack();
        boolean allowPartialSurfaces = ClimbingClawsConfig.enableCanopyGripEffect()
                && getEnchantmentLevel(clawsStack, player, ModEnchantments.CANOPY_GRIP) > 0;
        SurfaceContact sideSurface = ClimbingClawsConfig.enableWallClimbing()
                ? findHorizontalSurface(level, player.getBoundingBox().inflate(0.08D, 0.0D, 0.08D), allowPartialSurfaces)
                : null;
        SurfaceContact ceilingSurface = ClimbingClawsConfig.enableCeilingClimbing()
                ? findSurface(level, player.getBoundingBox().move(0.0D, 0.12D, 0.0D).inflate(-0.02D, 0.0D, -0.02D), Direction.DOWN, allowPartialSurfaces)
                : null;
        if (sideSurface == null && ceilingSurface == null) {
            return false;
        }

        int wallSpringLevel = getEnchantmentLevel(clawsStack, player, ModEnchantments.WALL_SPRING);
        if (wallSpringLevel <= 0 || isWallSpringOnCooldown(player)) {
            return false;
        }

        Vec3 movement = player.getDeltaMovement();
        double horizontalVelocityLimit = ClimbingClawsConfig.horizontalVelocityLimit();
        double x = Mth.clamp(movement.x, -horizontalVelocityLimit, horizontalVelocityLimit);
        double z = Mth.clamp(movement.z, -horizontalVelocityLimit, horizontalVelocityLimit);
        double y = Math.max(movement.y, 0.0D) + getWallSpringBoost(wallSpringLevel);

        player.setDeltaMovement(x, y, z);
        player.fallDistance = 0.0F;
        player.hurtMarked = true;
        startWallSpringCooldown(player);
        startStatGraceWindow(player);
        playWallSpringParticles(player, sideSurface != null ? sideSurface : ceilingSurface);
        playWallSpringSound(player, sideSurface != null ? sideSurface : ceilingSurface);

        if (player instanceof ServerPlayer serverPlayer) {
            ModCriteriaTriggers.USE_WALL_SPRING.trigger(serverPlayer);
            PacketDistributor.sendToPlayer(serverPlayer, new WallSpringCooldownPayload(ClimbingClawsConfig.wallSpringCooldownTicks()));
        }

        player.awardStat(ModStats.WALL_SPRING_USES);
        damageClaws(player, equippedClaws, true, true, true);
        return true;
    }

    public static boolean applyClientBurst(Player player) {
        EquippedClaws equippedClaws = findActiveClaws(player);
        if (!ClimbingClawsConfig.enableClimbing()
                || !ClimbingClawsConfig.enableWallSpring()
                || !player.level().isClientSide()
                || player.isSpectator()
                || player.isPassenger()
                || equippedClaws == null
                || (player.isShiftKeyDown() && !ClimbingClawsConfig.allowWallSpringWhileSneaking())) {
            return false;
        }

        Level level = player.level();
        ItemStack clawsStack = equippedClaws.stack();
        boolean allowPartialSurfaces = ClimbingClawsConfig.enableCanopyGripEffect()
                && getEnchantmentLevel(clawsStack, player, ModEnchantments.CANOPY_GRIP) > 0;
        SurfaceContact sideSurface = ClimbingClawsConfig.enableWallClimbing()
                ? findHorizontalSurface(level, player.getBoundingBox().inflate(0.08D, 0.0D, 0.08D), allowPartialSurfaces)
                : null;
        SurfaceContact ceilingSurface = ClimbingClawsConfig.enableCeilingClimbing()
                ? findSurface(level, player.getBoundingBox().move(0.0D, 0.12D, 0.0D).inflate(-0.02D, 0.0D, -0.02D), Direction.DOWN, allowPartialSurfaces)
                : null;
        if (sideSurface == null && ceilingSurface == null) {
            return false;
        }

        int wallSpringLevel = getEnchantmentLevel(clawsStack, player, ModEnchantments.WALL_SPRING);
        if (wallSpringLevel <= 0 || clientWallSpringCooldownTicks > 0) {
            return false;
        }

        Vec3 movement = player.getDeltaMovement();
        double horizontalVelocityLimit = ClimbingClawsConfig.horizontalVelocityLimit();
        double x = Mth.clamp(movement.x, -horizontalVelocityLimit, horizontalVelocityLimit);
        double z = Mth.clamp(movement.z, -horizontalVelocityLimit, horizontalVelocityLimit);
        double y = Math.max(movement.y, 0.0D) + getWallSpringBoost(wallSpringLevel);

        player.setDeltaMovement(x, y, z);
        player.fallDistance = 0.0F;
        player.hurtMarked = true;
        clientWallSpringCooldownTicks = ClimbingClawsConfig.wallSpringCooldownTicks();
        return true;
    }

    private static void tickWallSpringCooldown(Player player) {
        if (player.level().isClientSide()) {
            return;
        }

        UUID playerId = player.getUUID();
        Integer remainingTicks = WALL_SPRING_COOLDOWNS.get(playerId);
        if (remainingTicks == null) {
            return;
        }

        if (remainingTicks <= 1) {
            WALL_SPRING_COOLDOWNS.remove(playerId);
            return;
        }

        WALL_SPRING_COOLDOWNS.put(playerId, remainingTicks - 1);
    }

    private static boolean isWallSpringOnCooldown(Player player) {
        return WALL_SPRING_COOLDOWNS.getOrDefault(player.getUUID(), 0) > 0;
    }

    private static void startWallSpringCooldown(Player player) {
        int cooldownTicks = ClimbingClawsConfig.wallSpringCooldownTicks();
        if (cooldownTicks > 0) {
            WALL_SPRING_COOLDOWNS.put(player.getUUID(), cooldownTicks);
        }
    }

    private static void tickStatGraceWindow(Player player) {
        if (player.level().isClientSide()) {
            return;
        }

        UUID playerId = player.getUUID();
        Integer remainingTicks = STAT_GRACE_WINDOWS.get(playerId);
        if (remainingTicks == null) {
            return;
        }

        if (remainingTicks <= 1) {
            STAT_GRACE_WINDOWS.remove(playerId);
            return;
        }

        STAT_GRACE_WINDOWS.put(playerId, remainingTicks - 1);
    }

    private static void startStatGraceWindow(Player player) {
        if (CLIMBING_STAT_GRACE_TICKS > 0) {
            STAT_GRACE_WINDOWS.put(player.getUUID(), CLIMBING_STAT_GRACE_TICKS);
        }
    }

    private static void triggerAdvancements(Player player, boolean movingIntoWall, boolean hanging, boolean touchingCeilingSurface, boolean touchingPartialSurface) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        if (movingIntoWall) {
            ModCriteriaTriggers.CLIMB_WITH_CLAWS.trigger(serverPlayer);
        }
        if (hanging) {
            ModCriteriaTriggers.HANG_WITH_CLAWS.trigger(serverPlayer);
        }
        if (touchingCeilingSurface) {
            ModCriteriaTriggers.CLING_TO_CEILING.trigger(serverPlayer);
        }
        if (touchingPartialSurface) {
            ModCriteriaTriggers.CLIMB_PARTIAL_SURFACE.trigger(serverPlayer);
        }
    }

    private static boolean isPartialSurface(Level level, SurfaceContact contact) {
        return contact != null && !contact.state().isFaceSturdy(level, contact.pos(), contact.face());
    }

    private static void awardClimbingStats(Player player, boolean movingIntoWall, boolean descending, boolean hanging) {
        if (player.level().isClientSide()) {
            return;
        }

        if (movingIntoWall || descending) {
            player.awardStat(ModStats.CLIMBING_CLAWS_TIME);
        }

        if (hanging) {
            player.awardStat(ModStats.CLIMBING_CLAWS_HANG_TIME);
        }
    }

    private static void trackClimbingDistance(Player player, boolean attachedToSurface) {
        if (player.level().isClientSide()) {
            return;
        }

        UUID playerId = player.getUUID();
        Double lastTrackedHeight = LAST_TRACKED_HEIGHTS.get(playerId);
        double currentHeight = player.getY();
        boolean inGraceWindow = STAT_GRACE_WINDOWS.getOrDefault(playerId, 0) > 0;

        if (!attachedToSurface && !inGraceWindow) {
            LAST_TRACKED_HEIGHTS.remove(playerId);
            return;
        }

        if (lastTrackedHeight == null) {
            LAST_TRACKED_HEIGHTS.put(playerId, currentHeight);
            return;
        }

        double deltaHeight = currentHeight - lastTrackedHeight;
        int climbedCentimeters = (int) Math.round(Math.max(deltaHeight, 0.0D) * 100.0D);
        if (climbedCentimeters > 0) {
            player.awardStat(ModStats.CLIMBING_CLAWS_ONE_CM, climbedCentimeters);
        }

        int descendedCentimeters = (int) Math.round(Math.max(-deltaHeight, 0.0D) * 100.0D);
        if (descendedCentimeters > 0) {
            player.awardStat(ModStats.CLIMBING_CLAWS_DESCEND_ONE_CM, descendedCentimeters);
        }

        LAST_TRACKED_HEIGHTS.put(playerId, currentHeight);
    }

    private static void clearTrackedHeight(Player player) {
        if (player.level().isClientSide()) {
            return;
        }

        LAST_TRACKED_HEIGHTS.remove(player.getUUID());
        STAT_GRACE_WINDOWS.remove(player.getUUID());
    }

    private static void playWallSpringParticles(Player player, SurfaceContact contact) {
        if (!(player.level() instanceof ServerLevel serverLevel) || contact == null) {
            return;
        }

        serverLevel.sendParticles(
                new BlockParticleOption(ParticleTypes.BLOCK, contact.state()),
                player.getX(),
                player.getY() + 0.85D,
                player.getZ(),
                WALL_SPRING_BLOCK_PARTICLE_COUNT,
                0.18D,
                0.2D,
                0.18D,
                0.04D
        );
        serverLevel.sendParticles(
                ParticleTypes.CLOUD,
                player.getX(),
                player.getY() + 0.6D,
                player.getZ(),
                WALL_SPRING_CLOUD_PARTICLE_COUNT,
                0.08D,
                0.08D,
                0.08D,
                0.01D
        );
    }

    private static void playWallSpringSound(Player player, SurfaceContact contact) {
        if (!(player.level() instanceof ServerLevel serverLevel) || contact == null) {
            return;
        }

        serverLevel.playSound(
                null,
                player.getX(),
                player.getY() + 0.5D,
                player.getZ(),
                SoundEvents.CHAIN_HIT,
                SoundSource.PLAYERS,
                0.45F,
                1.1F + serverLevel.getRandom().nextFloat() * 0.1F
        );
        serverLevel.playSound(
                null,
                player.getX(),
                player.getY() + 0.5D,
                player.getZ(),
                SoundEvents.WIND_CHARGE_BURST.value(),
                SoundSource.PLAYERS,
                0.35F,
                1.2F + serverLevel.getRandom().nextFloat() * 0.1F
        );
        serverLevel.playSound(
                null,
                player.getX(),
                player.getY() + 0.5D,
                player.getZ(),
                contact.state().getSoundType().getStepSound(),
                SoundSource.PLAYERS,
                0.2F,
                0.85F + serverLevel.getRandom().nextFloat() * 0.1F
        );
    }

    private static EquippedClaws findActiveClaws(Player player) {
        if (isUsingClimbingClaws(player)) {
            return new EquippedClaws(player.getUseItem(), ClawsSource.HAND, getUsedEquipmentSlot(player));
        }

        if (ModList.get().isLoaded(CURIOS_MOD_ID)) {
            return CuriosCompat.findEquippedClaws(player)
                    .map(stack -> new EquippedClaws(stack, ClawsSource.CURIO_HANDS, EquipmentSlot.OFFHAND))
                    .orElse(null);
        }

        return null;
    }

    private static boolean isUsingClimbingClaws(Player player) {
        return player.isUsingItem()
                && ClimbingClawsConfig.isHandUseAllowed(player.getUsedItemHand())
                && isClimbingClaws(player.getUseItem());
    }

    private static EquipmentSlot getUsedEquipmentSlot(Player player) {
        return player.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
    }

    private static boolean isClimbingClaws(ItemStack stack) {
        return stack.is(ModItems.CLIMBING_CLAWS.get());
    }

    private static SurfaceContact findHorizontalSurface(Level level, AABB area, boolean allowPartialSurfaces) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            SurfaceContact contact = findSurface(level, area, direction, allowPartialSurfaces);
            if (contact != null) {
                return contact;
            }
        }
        return null;
    }

    private static SurfaceContact findSurface(Level level, AABB area, Direction face, boolean allowPartialSurfaces) {
        int minX = Mth.floor(area.minX);
        int maxX = Mth.floor(area.maxX);
        int minY = Mth.floor(area.minY);
        int maxY = Mth.floor(area.maxY);
        int minZ = Mth.floor(area.minZ);
        int maxZ = Mth.floor(area.maxZ);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    if (isValidClimbSurface(state, level, pos, face, allowPartialSurfaces)) {
                        return new SurfaceContact(pos, state, face);
                    }
                }
            }
        }

        return null;
    }

    private static boolean isValidClimbSurface(BlockState state, Level level, BlockPos pos, Direction face, boolean allowPartialSurfaces) {
        if (state.isAir()) {
            return false;
        }

        if (state.isFaceSturdy(level, pos, face)) {
            return true;
        }

        if (!allowPartialSurfaces) {
            return false;
        }

        VoxelShape collisionShape = state.getCollisionShape(level, pos);
        if (collisionShape.isEmpty()) {
            return false;
        }

        return !collisionShape.getFaceShape(face).isEmpty();
    }

    private static double getClimbSpeed(ItemStack stack, Player player, double baseSpeed) {
        int efficiencyLevel = getEnchantmentLevel(stack, player, EFFICIENCY);
        return baseSpeed + (efficiencyLevel * ClimbingClawsConfig.efficiencySpeedBonus());
    }

    private static double getWallSpringBoost(int level) {
        return level >= 2 ? ClimbingClawsConfig.wallSpringLevelTwoBoost() : ClimbingClawsConfig.wallSpringLevelOneBoost();
    }

    private static int getEnchantmentLevel(ItemStack stack, Player player, ResourceKey<Enchantment> enchantmentKey) {
        if (stack.isEmpty()) {
            return 0;
        }

        return ModEnchantments.getLevel(stack, player.registryAccess(), enchantmentKey);
    }

    private static void damageClaws(Player player, EquippedClaws equippedClaws, boolean activeClimb, boolean attachedToSurface, boolean springBurst) {
        if (!ClimbingClawsConfig.enableDurabilityDamage() || player.level().isClientSide() || !attachedToSurface) {
            return;
        }

        ItemStack stack = equippedClaws.stack();
        if (!isClimbingClaws(stack)) {
            return;
        }

        if (springBurst) {
            hurtAndBreakClaws(player, equippedClaws, ClimbingClawsConfig.wallSpringDamageAmount());
        }

        int interval = activeClimb ? ClimbingClawsConfig.activeClimbDamageIntervalTicks() : ClimbingClawsConfig.clingDamageIntervalTicks();
        if (player.tickCount % interval != 0) {
            return;
        }

        hurtAndBreakClaws(player, equippedClaws, ClimbingClawsConfig.climbingDamageAmount());
    }

    private static void hurtAndBreakClaws(Player player, EquippedClaws equippedClaws, int amount) {
        if (amount <= 0) {
            return;
        }

        if (equippedClaws.source() == ClawsSource.CURIO_HANDS) {
            CuriosCompat.hurtAndBreakClaws(player, equippedClaws.stack(), amount);
            return;
        }

        equippedClaws.stack().hurtAndBreak(amount, player, equippedClaws.slot());
    }

    private static void playClimbFeedback(Player player, Level level, SurfaceContact contact, boolean ceilingClimb) {
        if (contact == null || !level.isClientSide()) {
            return;
        }

        level.addParticle(
                new BlockParticleOption(ParticleTypes.BLOCK, contact.state()),
                player.getX() + (level.getRandom().nextDouble() - 0.5D) * 0.28D,
                player.getY() + (ceilingClimb ? 1.7D : 0.85D),
                player.getZ() + (level.getRandom().nextDouble() - 0.5D) * 0.28D,
                (level.getRandom().nextDouble() - 0.5D) * 0.025D,
                ceilingClimb ? -0.01D : 0.02D,
                (level.getRandom().nextDouble() - 0.5D) * 0.025D
        );

        if (level.getRandom().nextFloat() < 0.65F) {
            level.playLocalSound(
                    player.getX(),
                    player.getY() + 0.5D,
                    player.getZ(),
                    contact.state().getSoundType().getStepSound(),
                    SoundSource.PLAYERS,
                    0.18F,
                    0.9F + level.getRandom().nextFloat() * 0.15F,
                    false
            );
        }
    }

    private static void playClingFeedback(Player player, Level level, SurfaceContact contact) {
        if (contact == null || !level.isClientSide() || level.getRandom().nextFloat() >= 0.25F) {
            return;
        }

        level.addParticle(
                new BlockParticleOption(ParticleTypes.BLOCK, contact.state()),
                player.getX() + (level.getRandom().nextDouble() - 0.5D) * 0.18D,
                player.getY() + 0.75D,
                player.getZ() + (level.getRandom().nextDouble() - 0.5D) * 0.18D,
                0.0D,
                0.01D,
                0.0D
        );

        level.playLocalSound(
                player.getX(),
                player.getY() + 0.5D,
                player.getZ(),
                contact.state().getSoundType().getStepSound(),
                SoundSource.PLAYERS,
                0.08F,
                0.85F + level.getRandom().nextFloat() * 0.1F,
                false
        );
    }

    private record SurfaceContact(BlockPos pos, BlockState state, Direction face) {
    }

    private record EquippedClaws(ItemStack stack, ClawsSource source, EquipmentSlot slot) {
    }

    private enum ClawsSource {
        HAND,
        CURIO_HANDS
    }
}
