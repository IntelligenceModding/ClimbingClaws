package de.artemis.climbingclaws.client;

import de.artemis.climbingclaws.common.event.ClimbingClawsClimbHandler;
import net.minecraft.client.Minecraft;
import de.artemis.climbingclaws.common.network.ClimbingBurstPayload;
import de.artemis.climbingclaws.common.network.ClimbingInputPayload;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.compat.curios.client.CuriosClientCompat;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import de.artemis.climbingclaws.common.network.ModPayloads;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.event.TickEvent;

public final class ClientModEvents {
    private static final String CURIOS_MOD_ID = "curios";
    private static Player inputPlayer;
    private static boolean sentMovingInput;

    private ClientModEvents() {
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        if (ModList.get().isLoaded(CURIOS_MOD_ID)) {
            event.enqueueWork(CuriosClientCompat::registerRenderers);
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
        boolean moving = ClimbingClawsClimbHandler.hasActiveClaws(player)
                && ClimbingClawsClimbHandler.hasHorizontalInput(player);
        if (moving != sentMovingInput) {
            ModPayloads.CHANNEL.sendToServer(new ClimbingInputPayload(moving));
            sentMovingInput = moving;
        }

        if (minecraft.options.keyJump.consumeClick() && canUseWallSpring(player)) {
            ModPayloads.CHANNEL.sendToServer(ClimbingBurstPayload.INSTANCE);
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
        return player != null && getEnchantmentLevel(stack) > 0;
    }

    private static int getEnchantmentLevel(ItemStack stack) {
        return stack.getEnchantmentLevel(ModEnchantments.WALL_SPRING.get());
    }
}
