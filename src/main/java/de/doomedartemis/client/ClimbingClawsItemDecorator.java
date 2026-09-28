package de.doomedartemis.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public final class ClimbingClawsItemDecorator {
    private ClimbingClawsItemDecorator() {
    }

    public static void renderCooldownOverlay(GuiGraphics guiGraphics, ItemStack stack, int xOffset, int yOffset) {
        float cooldownPercent = ClientModEvents.getWallSpringCooldownPercent(stack);
        if (cooldownPercent <= 0.0F) {
            return;
        }

        int minY = yOffset + Mth.floor(16.0F * (1.0F - cooldownPercent));
        int maxY = minY + Mth.ceil(16.0F * cooldownPercent);
        guiGraphics.fill(RenderPipelines.GUI, xOffset, minY, xOffset + 16, maxY, Integer.MAX_VALUE);
    }
}
