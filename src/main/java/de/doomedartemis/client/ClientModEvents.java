package de.doomedartemis.client;

import de.doomedartemis.common.event.ClimbingClawsClimbHandler;
import de.doomedartemis.common.network.ClimbingBurstPayload;
import de.doomedartemis.common.registry.ModEnchantments;
import de.doomedartemis.common.registry.ModItems;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class ClientModEvents {
    private ClientModEvents() {
    }

    public static void onClientTick(Minecraft minecraft) {
        Player player = minecraft.player;

        if (player == null) {
            ClimbingClawsClimbHandler.clearClientWallSpringCooldown();
            return;
        }

        ClimbingClawsClimbHandler.onPlayerTick(player);
        ClimbingClawsClimbHandler.tickClientWallSpringCooldown();

        if (minecraft.options.keyJump.consumeClick()
                && canUseWallSpring(player)
                && ClientPlayNetworking.canSend(ClimbingBurstPayload.TYPE)) {
            ClientPlayNetworking.send(ClimbingBurstPayload.INSTANCE);
        }
    }

    public static float getWallSpringCooldownPercent(ItemStack stack) {
        if (!ClientConfig.showWallSpringCooldownOverlay() || !stack.is(ModItems.CLIMBING_CLAWS) || !hasWallSpring(stack)) {
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
