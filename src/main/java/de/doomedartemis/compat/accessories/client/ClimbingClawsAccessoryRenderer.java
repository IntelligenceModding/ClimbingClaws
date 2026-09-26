package de.doomedartemis.compat.accessories.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class ClimbingClawsAccessoryRenderer implements AccessoryRenderer {
    @Override
    public <T extends LivingEntity> void render(
            ItemStack stack,
            SlotReference reference,
            PoseStack poseStack,
            EntityModel<T> model,
            MultiBufferSource bufferSource,
            int packedLight,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {
        if (!(model instanceof ArmedModel armedModel)) {
            return;
        }

        LivingEntity entity = reference.entity();
        HumanoidArm arm = getOffhandArm(entity);

        poseStack.pushPose();
        armedModel.translateToHand(arm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        boolean leftHand = arm == HumanoidArm.LEFT;
        poseStack.translate((leftHand ? -1 : 1) / 16.0F, 2.0F / 16.0F, -10.0F / 16.0F);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                entity,
                stack,
                getDisplayContext(arm),
                leftHand,
                poseStack,
                bufferSource,
                entity.level(),
                packedLight,
                0,
                entity.getId()
        );
        poseStack.popPose();
    }

    private static HumanoidArm getOffhandArm(LivingEntity entity) {
        return entity.getMainArm() == HumanoidArm.RIGHT ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }

    private static ItemDisplayContext getDisplayContext(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }
}
