package de.artemis.climbingclaws.mixin;

import de.artemis.climbingclaws.common.event.ClimbingClawsAnvilHandler;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ResultContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {
    @Shadow
    @Final
    private DataSlot cost;

    @Inject(method = "createResult", at = @At("RETURN"))
    private void climbingclaws$filterUnsupportedClawsEnchantments(CallbackInfo ci) {
        ItemCombinerMenuAccessor accessor = (ItemCombinerMenuAccessor) this;
        Container inputSlots = accessor.climbingclaws$getInputSlots();
        if (!ClimbingClawsAnvilHandler.blocksAnvilCombination(
                inputSlots.getItem(0),
                inputSlots.getItem(1),
                accessor.climbingclaws$getPlayer().registryAccess())) {
            return;
        }

        ResultContainer resultSlots = accessor.climbingclaws$getResultSlots();
        resultSlots.setItem(0, ItemStack.EMPTY);
        this.cost.set(0);
    }
}
