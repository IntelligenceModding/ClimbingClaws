package de.artemis.climbingclaws.compat.curios.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public final class ClimbingClawsCurioRenderer implements ICurioRenderer {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource bufferSource,
            int packedLight,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {
        if (!(renderLayerParent.getModel() instanceof ArmedModel armedModel)) {
            return;
        }

        LivingEntity entity = slotContext.entity();
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
