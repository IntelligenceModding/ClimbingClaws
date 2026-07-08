package de.artemis.climbingclaws.common.event;

import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
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

        var enchantments = event.getPlayer().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        if (hasClimbingClawsExclusiveEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(left), enchantments)
                || hasClimbingClawsExclusiveEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(right), enchantments)) {
            event.setCanceled(true);
        }
    }

    private static boolean hasClimbingClawsExclusiveEnchantment(net.minecraft.world.item.enchantment.ItemEnchantments enchantmentsOnStack, net.minecraft.core.HolderLookup.RegistryLookup<Enchantment> enchantments) {
        return enchantmentsOnStack.getLevel(enchantments.getOrThrow(ModEnchantments.WALL_SPRING)) > 0
                || enchantmentsOnStack.getLevel(enchantments.getOrThrow(ModEnchantments.CANOPY_GRIP)) > 0;
    }
}
