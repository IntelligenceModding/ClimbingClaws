package de.doomedartemis.common.event;

import de.doomedartemis.common.registry.ModEnchantments;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public final class ClimbingClawsTooltipHandler {
    private ClimbingClawsTooltipHandler() {
    }

    public static void onItemTooltip(ItemStack stack, Item.TooltipContext context, TooltipFlag tooltipType, List<Component> lines) {
        HolderLookup.Provider registries = context.registries();
        if (registries == null) {
            return;
        }

        var enchantments = registries.lookup(Registries.ENCHANTMENT);
        if (enchantments.isEmpty()) {
            return;
        }

        var stackEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);

        boolean hasWallSpring = enchantments.get().get(ModEnchantments.WALL_SPRING)
                .map(enchantment -> stackEnchantments.getLevel(enchantment) > 0)
                .orElse(false);
        boolean hasCanopyGrip = enchantments.get().get(ModEnchantments.CANOPY_GRIP)
                .map(enchantment -> stackEnchantments.getLevel(enchantment) > 0)
                .orElse(false);
        if (!hasWallSpring && !hasCanopyGrip) {
            return;
        }

        lines.add(Component.empty());
        if (hasWallSpring) {
            lines.add(Component.translatable("tooltip.climbingclaws.wall_spring").withStyle(ChatFormatting.GRAY));
        }
        if (hasCanopyGrip) {
            lines.add(Component.translatable("tooltip.climbingclaws.canopy_grip").withStyle(ChatFormatting.GRAY));
        }
    }
}
