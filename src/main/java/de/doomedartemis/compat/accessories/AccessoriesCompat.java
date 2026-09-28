package de.doomedartemis.compat.accessories;

import de.doomedartemis.common.registry.ModItems;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class AccessoriesCompat {
    public static final String ACCESSORIES_MOD_ID = "accessories";
    private static final String HAND_SLOT = "hand";

    private AccessoriesCompat() {
    }

    public static void register() {
        AccessoriesAPI.registerAccessory(ModItems.CLIMBING_CLAWS, new Accessory() {
            @Override
            public boolean canEquip(ItemStack stack, SlotReference reference) {
                return HAND_SLOT.equals(reference.slotName());
            }

            @Override
            public boolean canEquipFromUse(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canEquipFromUse(ItemStack stack, SlotReference reference) {
                return false;
            }
        });
    }

    public static Optional<ItemStack> findEquippedClaws(Player player) {
        AccessoriesCapability capability = AccessoriesCapability.get(player);
        if (capability == null) {
            return Optional.empty();
        }

        AccessoriesContainer container = capability.getContainers().get(HAND_SLOT);
        if (container == null) {
            return Optional.empty();
        }

        for (int i = 0; i < container.getSize(); i++) {
            ItemStack stack = container.getAccessories().getItem(i);
            if (isClimbingClaws(stack)) {
                return Optional.of(stack);
            }
        }

        return Optional.empty();
    }

    public static void hurtAndBreakClaws(Player player, ItemStack stack, int amount) {
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        ServerPlayer serverPlayer = player instanceof ServerPlayer candidate ? candidate : null;
        findEquippedClawsReference(player, stack)
                .ifPresentOrElse(
                        reference -> stack.hurtAndBreak(amount, serverLevel, serverPlayer, item -> AccessoriesAPI.breakStack(reference)),
                        () -> stack.hurtAndBreak(amount, serverLevel, serverPlayer, item -> {
                        })
                );
    }

    private static Optional<SlotReference> findEquippedClawsReference(Player player, ItemStack stack) {
        AccessoriesCapability capability = AccessoriesCapability.get(player);
        if (capability == null) {
            return Optional.empty();
        }

        AccessoriesContainer container = capability.getContainers().get(HAND_SLOT);
        if (container == null) {
            return Optional.empty();
        }

        for (int i = 0; i < container.getSize(); i++) {
            if (container.getAccessories().getItem(i) == stack) {
                return Optional.of(container.createReference(i));
            }
        }

        return Optional.empty();
    }

    private static boolean isClimbingClaws(ItemStack stack) {
        return stack.is(ModItems.CLIMBING_CLAWS);
    }
}
