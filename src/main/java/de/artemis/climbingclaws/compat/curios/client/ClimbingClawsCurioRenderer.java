package de.artemis.climbingclaws.compat.curios.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public final class ClimbingClawsCurioRenderer implements ICurioRenderer {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int packedLight,
            S renderState,
            RenderLayerParent<S, M> renderLayerParent,
            EntityRendererProvider.Context context,
            float yRotation,
            float xRotation) {
        if (!(renderState instanceof ArmedEntityRenderState armedState) || !(renderLayerParent.getModel() instanceof ArmedModel armedModel)) {
            return;
        }

        HumanoidArm arm = getOffhandArm(armedState);
        ItemStackRenderState itemRenderState = new ItemStackRenderState();
        context.getItemModelResolver().updateForLiving(itemRenderState, stack, getThirdPersonDisplayContext(arm), slotContext.entity());

        poseStack.pushPose();
        armedModel.translateToHand(armedState, arm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        boolean leftHand = arm == HumanoidArm.LEFT;
        poseStack.translate((leftHand ? -1 : 1) / 16.0F, 2.0F / 16.0F, -10.0F / 16.0F);
        itemRenderState.submit(poseStack, submitNodeCollector, packedLight, OverlayTexture.NO_OVERLAY, armedState.outlineColor);
        poseStack.popPose();
    }

    @Override
    public void renderFirstPersonHand(
            ItemStack stack,
            SlotContext slotContext,
            HumanoidArm arm,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            AvatarRenderState avatarRenderState,
            AbstractClientPlayer clientPlayer,
            int packedLight) {
        if (arm != getOffhandArm(avatarRenderState)) {
            return;
        }

        ItemStackRenderState itemRenderState = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver().updateForLiving(itemRenderState, stack, getFirstPersonDisplayContext(arm), clientPlayer);
        itemRenderState.submit(poseStack, submitNodeCollector, packedLight, OverlayTexture.NO_OVERLAY, avatarRenderState.outlineColor);
    }

    private static HumanoidArm getOffhandArm(ArmedEntityRenderState state) {
        return state.mainArm == HumanoidArm.RIGHT ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }

    private static ItemDisplayContext getThirdPersonDisplayContext(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    private static ItemDisplayContext getFirstPersonDisplayContext(HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? ItemDisplayContext.FIRST_PERSON_LEFT_HAND : ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;
    }
}
