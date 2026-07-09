package de.artemis.climbingclaws.common.event;

import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

public final class ClimbingClawsAnvilHandler {
    private ClimbingClawsAnvilHandler() {
    }

    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isEmpty() || left.is(Items.ENCHANTED_BOOK) || left.is(ModItems.CLIMBING_CLAWS.get())) {
            return;
        }

        if (hasClimbingClawsExclusiveEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(left), event.getPlayer().registryAccess())
                || hasClimbingClawsExclusiveEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(right), event.getPlayer().registryAccess())) {
            event.setCanceled(true);
        }
    }

    private static boolean hasClimbingClawsExclusiveEnchantment(net.minecraft.world.item.enchantment.ItemEnchantments enchantmentsOnStack, net.minecraft.core.HolderLookup.Provider registries) {
        return ModEnchantments.get(registries, ModEnchantments.WALL_SPRING).map(enchantmentsOnStack::getLevel).orElse(0) > 0
                || ModEnchantments.get(registries, ModEnchantments.CANOPY_GRIP).map(enchantmentsOnStack::getLevel).orElse(0) > 0;
    }
}
