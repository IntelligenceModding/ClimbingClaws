package de.artemis.climbingclaws.client;

import de.artemis.climbingclaws.common.event.ClimbingClawsClimbHandler;
import net.minecraft.client.Minecraft;
import de.artemis.climbingclaws.common.network.ClimbingBurstPayload;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public final class ClientModEvents {
    private ClientModEvents() {
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null) {
            ClimbingClawsClimbHandler.clearClientWallSpringCooldown();
            return;
        }

        ClimbingClawsClimbHandler.tickClientWallSpringCooldown();

        if (minecraft.options.keyJump.consumeClick() && canUseWallSpring(player)) {
            ClimbingClawsClimbHandler.applyClientBurst(player);
            ClientPacketDistributor.sendToServer(ClimbingBurstPayload.INSTANCE);
        }
    }

    public static void onRegisterItemDecorations(RegisterItemDecorationsEvent event) {
        event.register(ModItems.CLIMBING_CLAWS.get(), ClimbingClawsItemDecorator.INSTANCE);
    }

    public static float getWallSpringCooldownPercent(ItemStack stack) {
        if (!stack.is(ModItems.CLIMBING_CLAWS.get()) || !hasWallSpring(stack)) {
            return 0.0F;
        }

        return ClimbingClawsClimbHandler.getClientWallSpringCooldownPercent();
    }

    private static boolean canUseWallSpring(Player player) {
        return ClimbingClawsClimbHandler.getClientWallSpringCooldownPercent() <= 0.0F
                && player.isUsingItem()
                && player.getUsedItemHand() == InteractionHand.OFF_HAND
                && player.getUseItem().is(ModItems.CLIMBING_CLAWS.get())
                && getEnchantmentLevel(player.getUseItem(), player) > 0;
    }

    private static boolean hasWallSpring(ItemStack stack) {
        Player player = Minecraft.getInstance().player;
        return player != null && getEnchantmentLevel(stack, player) > 0;
    }

    private static int getEnchantmentLevel(ItemStack stack, Player player) {
        return ModEnchantments.getLevel(stack, player.registryAccess(), ModEnchantments.WALL_SPRING);
    }
}
