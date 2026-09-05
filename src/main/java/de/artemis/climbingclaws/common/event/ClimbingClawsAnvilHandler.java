package de.artemis.climbingclaws.common.event;

import de.artemis.climbingclaws.common.item.ClimbingClawsItem;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

public final class ClimbingClawsAnvilHandler {
    private ClimbingClawsAnvilHandler() {
    }

    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isEmpty() || left.is(Items.ENCHANTED_BOOK)) {
            return;
        }

        if (left.is(ModItems.CLIMBING_CLAWS.get())) {
            if (hasUnsupportedClimbingClawsEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(right))) {
                event.setCanceled(true);
            }
            return;
        }

        var enchantments = event.getPlayer().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        if (hasClimbingClawsExclusiveEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(left), enchantments)
                || hasClimbingClawsExclusiveEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(right), enchantments)) {
            event.setCanceled(true);
        }
    }

    private static boolean hasClimbingClawsExclusiveEnchantment(ItemEnchantments enchantmentsOnStack, net.minecraft.core.HolderLookup.RegistryLookup<Enchantment> enchantments) {
        return enchantmentsOnStack.getLevel(enchantments.getOrThrow(ModEnchantments.WALL_SPRING)) > 0
                || enchantmentsOnStack.getLevel(enchantments.getOrThrow(ModEnchantments.CANOPY_GRIP)) > 0;
    }

    private static boolean hasUnsupportedClimbingClawsEnchantment(ItemEnchantments enchantmentsOnStack) {
        return enchantmentsOnStack.entrySet().stream()
                .anyMatch(entry -> entry.getIntValue() > 0 && !ClimbingClawsItem.supportsClimbingClawsEnchantment(entry.getKey()));
    }
}
