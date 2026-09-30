package de.doomedartemis.mixin.client;

import de.doomedartemis.client.ClimbingClawsItemDecorator;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsMixin {
    @Inject(method = "itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("RETURN"))
    private void climbingclaws$renderCooldownOverlay(Font font, ItemStack stack, int x, int y, @Nullable String text, CallbackInfo ci) {
        ClimbingClawsItemDecorator.renderCooldownOverlay((GuiGraphicsExtractor) (Object) this, stack, x, y);
    }
}
