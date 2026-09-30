package de.artemis.climbingclaws.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ResultContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemCombinerMenu.class)
public interface ItemCombinerMenuAccessor {
    @Accessor("inputSlots")
    Container climbingclaws$getInputSlots();

    @Accessor("resultSlots")
    ResultContainer climbingclaws$getResultSlots();

    @Accessor("player")
    Player climbingclaws$getPlayer();
}
