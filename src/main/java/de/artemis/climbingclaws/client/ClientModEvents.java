package de.artemis.climbingclaws.client;

import de.artemis.climbingclaws.common.event.ClimbingClawsClimbHandler;
import net.minecraft.client.Minecraft;
import de.artemis.climbingclaws.common.network.ClimbingBurstPayload;
import de.artemis.climbingclaws.common.network.ClimbingInputPayload;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import de.artemis.climbingclaws.common.network.ModPayloads;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.network.PacketDistributor;

public final class ClientModEvents {
    private static final String CURIOS_MOD_ID = "curios";
    private static Player inputPlayer;
    private static boolean sentMovingInput;

    private ClientModEvents() {
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        // Curios has no Forge build for Minecraft 1.21.x, so this can never trigger on this
        // build; kept so Curios support can be restored with no re-authoring if a Forge build appears.
        if (ModList.get().isLoaded(CURIOS_MOD_ID)) {
            return;
        }
    }

    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null) {
            inputPlayer = null;
            sentMovingInput = false;
            ClimbingClawsClimbHandler.clearClientWallSpringCooldown();
            return;
        }

        ClimbingClawsClimbHandler.tickClientWallSpringCooldown();

        if (inputPlayer != player) {
            inputPlayer = player;
            sentMovingInput = false;
        }
        boolean moving = ClimbingClawsClimbHandler.isUsingClimbingClaws(player)
                && ClimbingClawsClimbHandler.hasHorizontalInput(player);
        if (moving != sentMovingInput) {
            ModPayloads.CHANNEL.send(new ClimbingInputPayload(moving), PacketDistributor.SERVER.noArg());
            sentMovingInput = moving;
        }

        if (minecraft.options.keyJump.consumeClick() && canUseWallSpring(player)) {
            ModPayloads.CHANNEL.send(ClimbingBurstPayload.INSTANCE, PacketDistributor.SERVER.noArg());
        }
    }

    public static void onRegisterItemDecorations(RegisterItemDecorationsEvent event) {
        event.register(ModItems.CLIMBING_CLAWS.get(), ClimbingClawsItemDecorator.INSTANCE);
    }

    public static float getWallSpringCooldownPercent(ItemStack stack) {
        if (!ClientConfig.showWallSpringCooldownOverlay() || !stack.is(ModItems.CLIMBING_CLAWS.get()) || !hasWallSpring(stack)) {
            return 0.0F;
        }

        return ClimbingClawsClimbHandler.getClientWallSpringCooldownPercent();
    }

    private static boolean canUseWallSpring(Player player) {
        return ClimbingClawsClimbHandler.getClientWallSpringCooldownPercent() <= 0.0F
                && ClimbingClawsClimbHandler.hasActiveWallSpring(player);
    }

    private static boolean hasWallSpring(ItemStack stack) {
        Player player = Minecraft.getInstance().player;
        return player != null && getEnchantmentLevel(stack, player) > 0;
    }

    private static int getEnchantmentLevel(ItemStack stack, Player player) {
        return ModEnchantments.getLevel(stack, player.registryAccess(), ModEnchantments.WALL_SPRING);
    }
}
