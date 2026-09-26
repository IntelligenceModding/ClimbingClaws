package de.doomedartemis.common.event;

import de.doomedartemis.common.item.ClimbingClawsItem;
import de.doomedartemis.common.registry.ModEnchantments;
import de.doomedartemis.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class ClimbingClawsAnvilHandler {
    private ClimbingClawsAnvilHandler() {
    }

    public static boolean blocksAnvilCombination(ItemStack left, ItemStack right, HolderLookup.Provider registries) {
        if (left.isEmpty() || left.is(Items.ENCHANTED_BOOK)) {
            return false;
        }

        if (left.is(ModItems.CLIMBING_CLAWS)) {
            return hasUnsupportedClimbingClawsEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(right));
        }

        var enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);
        return hasClimbingClawsExclusiveEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(left), enchantments)
                || hasClimbingClawsExclusiveEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(right), enchantments);
    }

    private static boolean hasClimbingClawsExclusiveEnchantment(ItemEnchantments enchantmentsOnStack, HolderLookup.RegistryLookup<Enchantment> enchantments) {
        return enchantmentsOnStack.getLevel(enchantments.getOrThrow(ModEnchantments.WALL_SPRING)) > 0
                || enchantmentsOnStack.getLevel(enchantments.getOrThrow(ModEnchantments.CANOPY_GRIP)) > 0;
    }

    private static boolean hasUnsupportedClimbingClawsEnchantment(ItemEnchantments enchantmentsOnStack) {
        return enchantmentsOnStack.entrySet().stream()
                .anyMatch(entry -> entry.getIntValue() > 0 && !ClimbingClawsItem.supportsClimbingClawsEnchantment(entry.getKey()));
    }
}
