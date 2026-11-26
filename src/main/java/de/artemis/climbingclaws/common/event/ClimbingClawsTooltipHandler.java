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
        var stackEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(event.getItemStack());

        boolean hasWallSpring = stackEnchantments.keySet().stream().anyMatch(enchantment -> enchantment.is(ModEnchantments.WALL_SPRING));
        boolean hasCanopyGrip = stackEnchantments.keySet().stream().anyMatch(enchantment -> enchantment.is(ModEnchantments.CANOPY_GRIP));
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
