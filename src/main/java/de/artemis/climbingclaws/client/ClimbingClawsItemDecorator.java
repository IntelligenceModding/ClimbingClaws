package de.artemis.climbingclaws.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;

public final class ClimbingClawsItemDecorator implements IItemDecorator {
    public static final ClimbingClawsItemDecorator INSTANCE = new ClimbingClawsItemDecorator();

    private ClimbingClawsItemDecorator() {
    }

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        float cooldownPercent = ClientModEvents.getWallSpringCooldownPercent(stack);
        if (cooldownPercent <= 0.0F) {
            return false;
        }

        int minY = yOffset + Mth.floor(16.0F * (1.0F - cooldownPercent));
        int maxY = minY + Mth.ceil(16.0F * cooldownPercent);
        guiGraphics.fill(RenderType.guiOverlay(), xOffset, minY, xOffset + 16, maxY, Integer.MAX_VALUE);
        return false;
    }
}
