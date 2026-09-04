package de.artemis.climbingclaws.compat.curios;

/*
 * Curios has no launchable 1.21.6 artifact for NeoForge 21.6.0-beta.
 *
import de.artemis.climbingclaws.common.registry.ModItems;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public final class CuriosCompat {
    private static final String HANDS_SLOT = "hands";

    private CuriosCompat() {
    }

    public static Optional<ItemStack> findEquippedClaws(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .flatMap(handler -> handler.findFirstCurio(CuriosCompat::isClimbingClaws, HANDS_SLOT))
                .map(SlotResult::stack);
    }

    public static void hurtAndBreakClaws(Player player, ItemStack stack, int amount) {
        if (!(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        CuriosApi.getCuriosInventory(player)
                .flatMap(handler -> handler.findFirstCurio(candidate -> candidate == stack, HANDS_SLOT))
                .ifPresentOrElse(
                        result -> stack.hurtAndBreak(amount, serverLevel, player, item -> CuriosApi.broadcastCurioBreakEvent(result.slotContext())),
                        () -> stack.hurtAndBreak(amount, serverLevel, player, item -> {
                        })
                );
    }

    private static boolean isClimbingClaws(ItemStack stack) {
        return stack.is(ModItems.CLIMBING_CLAWS.get());
    }
}
 */
