package de.artemis.climbingclaws.common.event;

import de.artemis.climbingclaws.common.registry.ModEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public final class ClimbingClawsTooltipHandler {
    private ClimbingClawsTooltipHandler() {
    }

    public static void onItemTooltip(ItemTooltipEvent event) {
        HolderLookup.Provider registries = event.getContext().registries();
        if (registries == null) {
            return;
        }

        var enchantments = registries.lookup(Registries.ENCHANTMENT);
        if (enchantments.isEmpty()) {
            return;
        }

        var stackEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(event.getItemStack());

        boolean hasWallSpring = enchantments.get().get(ModEnchantments.WALL_SPRING)
                .map(enchantment -> stackEnchantments.getLevel(enchantment) > 0)
                .orElse(false);
        boolean hasCanopyGrip = enchantments.get().get(ModEnchantments.CANOPY_GRIP)
                .map(enchantment -> stackEnchantments.getLevel(enchantment) > 0)
                .orElse(false);
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
