package de.artemis.climbingclaws.compat.curios;

import de.artemis.climbingclaws.common.registry.ModItems;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public final class CuriosCompat {
    private static final String HANDS_SLOT = "hands";

    private CuriosCompat() {
    }

    public static Optional<ItemStack> findEquippedClaws(Player player) {
        return CuriosApi.getCuriosInventory(player).resolve()
                .flatMap(handler -> handler.findCurios(HANDS_SLOT).stream().filter(result -> isClimbingClaws(result.stack())).findFirst())
                .map(SlotResult::stack);
    }

    public static void hurtAndBreakClaws(Player player, ItemStack stack, int amount) {
        if (player.level().isClientSide) {
            return;
        }

        CuriosApi.getCuriosInventory(player).resolve()
                .flatMap(handler -> handler.findCurios(HANDS_SLOT).stream().filter(result -> result.stack() == stack).findFirst())
                .ifPresentOrElse(
                        result -> stack.hurtAndBreak(amount, player, entity -> CuriosApi.broadcastCurioBreakEvent(result.slotContext())),
                        () -> stack.hurtAndBreak(amount, player, entity -> {
                        })
                );
    }

    private static boolean isClimbingClaws(ItemStack stack) {
        return stack.is(ModItems.CLIMBING_CLAWS.get());
    }
}
