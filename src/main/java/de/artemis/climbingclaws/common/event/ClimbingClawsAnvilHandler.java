package de.artemis.climbingclaws.common.event;

import de.artemis.climbingclaws.common.item.ClimbingClawsItem;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraftforge.event.AnvilUpdateEvent;

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
                rejectResult(event);
            }
            return;
        }

        if (hasClimbingClawsExclusiveEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(left))
                || hasClimbingClawsExclusiveEnchantment(EnchantmentHelper.getEnchantmentsForCrafting(right))) {
            rejectResult(event);
        }
    }

    private static void rejectResult(AnvilUpdateEvent event) {
        event.setCanceled(true);
        // Forge returns early on cancellation, so clear the previous preview explicitly.
        if (event.getPlayer().containerMenu instanceof AnvilMenu menu
                && menu.getSlot(0).getItem() == event.getLeft()
                && menu.getSlot(1).getItem() == event.getRight()) {
            menu.setMaximumCost(0);
            menu.getSlot(2).set(ItemStack.EMPTY);
            menu.broadcastChanges();
        }
    }

    private static boolean hasClimbingClawsExclusiveEnchantment(ItemEnchantments enchantmentsOnStack) {
        return enchantmentsOnStack.keySet().stream()
                .anyMatch(enchantment -> enchantment.is(ModEnchantments.WALL_SPRING) || enchantment.is(ModEnchantments.CANOPY_GRIP));
    }

    private static boolean hasUnsupportedClimbingClawsEnchantment(ItemEnchantments enchantmentsOnStack) {
        return enchantmentsOnStack.entrySet().stream()
                .anyMatch(entry -> entry.getIntValue() > 0 && !ClimbingClawsItem.supportsClimbingClawsEnchantment(entry.getKey()));
    }
}
