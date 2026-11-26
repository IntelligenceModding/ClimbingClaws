package de.artemis.climbingclaws.common.event;

import de.artemis.climbingclaws.common.item.ClimbingClawsItem;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;

import java.util.Map;

public final class ClimbingClawsAnvilHandler {
    private ClimbingClawsAnvilHandler() {
    }

    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        if (left.isEmpty() || left.is(Items.ENCHANTED_BOOK)) {
            return;
        }

        Map<Enchantment, Integer> additions = EnchantmentHelper.getEnchantments(right);
        if (left.is(ModItems.CLIMBING_CLAWS.get())) {
            if (additions.keySet().stream().anyMatch(enchantment -> !ClimbingClawsItem.supportsClimbingClawsEnchantment(enchantment))) {
                rejectResult(event);
            } else if ((right.is(Items.ENCHANTED_BOOK) || right.is(left.getItem()))
                    && additions.keySet().stream().anyMatch(enchantment -> !enchantment.canEnchant(left))) {
                combineClaws(event, additions);
            }
        } else if (containsExclusive(EnchantmentHelper.getEnchantments(left)) || containsExclusive(additions)) {
            rejectResult(event);
        }
    }

    // In 1.20.1 the vanilla anvil cannot apply weapon and mining categories to a shield item.
    private static void combineClaws(AnvilUpdateEvent event, Map<Enchantment, Integer> additions) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        ItemStack result = left.copy();
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(left);
        boolean book = right.is(Items.ENCHANTED_BOOK);
        long cost = event.getCost();
        boolean applied = false;
        for (var addition : additions.entrySet()) {
            Enchantment enchantment = addition.getKey();
            boolean compatible = true;
            for (Enchantment existing : enchantments.keySet()) {
                if (existing != enchantment && !enchantment.isCompatibleWith(existing)) {
                    compatible = false;
                    cost++;
                }
            }
            if (!compatible) {
                continue;
            }
            int current = enchantments.getOrDefault(enchantment, 0);
            int incoming = addition.getValue();
            int level = Math.min(enchantment.getMaxLevel(), current == incoming ? current + 1 : Math.max(current, incoming));
            int multiplier = switch (enchantment.getRarity()) {
                case COMMON -> 1;
                case UNCOMMON -> 2;
                case RARE -> 4;
                case VERY_RARE -> 8;
            };
            cost += (long) (book ? Math.max(1, multiplier / 2) : multiplier) * level;
            enchantments.put(enchantment, level);
            applied = true;
        }

        if (!applied) {
            rejectResult(event);
            return;
        }
        if (!book) {
            int maxDamage = left.getMaxDamage();
            int combinedDamage = Math.max(0, left.getDamageValue() + right.getDamageValue() - maxDamage - maxDamage * 12 / 100);
            if (combinedDamage < left.getDamageValue()) {
                cost += 2;
                result.setDamageValue(combinedDamage);
            }
        }
        String name = event.getName();
        if (name != null && !name.isBlank()) {
            if (!name.equals(left.getHoverName().getString())) {
                result.setHoverName(Component.literal(name));
                cost++;
            }
        } else if (left.hasCustomHoverName()) {
            result.resetHoverName();
            cost++;
        }

        if (cost >= 40 && !event.getPlayer().getAbilities().instabuild) {
            rejectResult(event);
            return;
        }
        result.setRepairCost(AnvilMenu.calculateIncreasedRepairCost(Math.max(left.getBaseRepairCost(), right.getBaseRepairCost())));
        EnchantmentHelper.setEnchantments(enchantments, result);
        event.setOutput(result);
        event.setCost((int) Math.min(cost, Integer.MAX_VALUE));
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

    private static boolean containsExclusive(Map<Enchantment, Integer> enchantments) {
        return enchantments.containsKey(ModEnchantments.WALL_SPRING.get())
                || enchantments.containsKey(ModEnchantments.CANOPY_GRIP.get());
    }
}
