package de.artemis.climbingclaws.common.event;

import de.artemis.climbingclaws.common.registry.ModEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public final class ClimbingClawsTooltipHandler {
    private ClimbingClawsTooltipHandler() {
    }

    public static void onItemTooltip(ItemTooltipEvent event) {
        var stackEnchantments = EnchantmentHelper.getEnchantments(event.getItemStack());

        boolean hasWallSpring = stackEnchantments.containsKey(ModEnchantments.WALL_SPRING.get());
        boolean hasCanopyGrip = stackEnchantments.containsKey(ModEnchantments.CANOPY_GRIP.get());
        if (!hasWallSpring && !hasCanopyGrip) {
            return;
        }

        event.getToolTip().add(Component.empty());
        if (hasWallSpring) {
            event.getToolTip().add(Component.translatable("tooltip.climbingclaws.wall_spring").withStyle(ChatFormatting.GRAY));
        }
        if (hasCanopyGrip) {
            event.getToolTip().add(Component.translatable("tooltip.climbingclaws.canopy_grip").withStyle(ChatFormatting.GRAY));
        }
    }
}
