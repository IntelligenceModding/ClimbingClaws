package de.doomedartemis.compat.accessories.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.wispforest.accessories.api.AccessoriesStorageLookup;
import io.wispforest.accessories.api.client.AccessoriesRenderStateKeys;
import io.wispforest.accessories.api.client.AccessoryRenderState;
import io.wispforest.accessories.api.client.RenderStateStorage;
import io.wispforest.accessories.api.client.renderers.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotPath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class ClimbingClawsAccessoryRenderer implements SimpleAccessoryRenderer {
    @Override
    public AccessoryRenderState createRenderState(
            ItemStack stack,
            SlotPath path,
            AccessoriesStorageLookup storageLookup,
            LivingEntity entity,
            LivingEntityRenderState entityState) {
        AccessoryRenderState state = AccessoryRenderState.setupState(path, stack, entity, entityState, false);
        HumanoidArm renderedArm = null;
        if (entityState instanceof RenderStateStorage renderStateStorage) {
            renderedArm = renderStateStorage.getStateData(AccessoriesRenderStateKeys.ARM);
            if (renderedArm != null) {
                state.setStateData(AccessoriesRenderStateKeys.ARM, renderedArm);
            }
        }
        ItemStackRenderState stackRenderState = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver().updateForLiving(
                stackRenderState,
                stack,
                displayContext(path),
                entity
        );
        state.setStateData(AccessoriesRenderStateKeys.ITEM_STACK_STATE, stackRenderState);
        return state;
    }

    @Override
    public <S extends LivingEntityRenderState> void render(
            AccessoryRenderState accessoryState,
            S entityState,
            EntityModel<S> model,
            PoseStack matrices,
            SubmitNodeCollector collector) {
        HumanoidArm renderedArm = accessoryState.getStateData(AccessoriesRenderStateKeys.ARM);
        if (renderedArm != null) {
            return;
        }

        SimpleAccessoryRenderer.super.render(accessoryState, entityState, model, matrices, collector);
    }

    @Override
    public <S extends LivingEntityRenderState> void align(
            AccessoryRenderState accessoryState,
            S entityState,
            EntityModel<S> model,
            PoseStack matrices) {
        if (!(model instanceof ArmedModel<?> armedModel)) {
            return;
        }

        SlotPath path = accessoryState.getStateData(AccessoriesRenderStateKeys.SLOT_PATH);
        HumanoidArm arm = arm(path);

        @SuppressWarnings("unchecked")
        ArmedModel<S> typedModel = (ArmedModel<S>) armedModel;
        typedModel.translateToHand(entityState, arm, matrices);
        HumanoidArm renderedArm = accessoryState.getStateData(AccessoriesRenderStateKeys.ARM);
        if (renderedArm != null) {
            int invert = arm == HumanoidArm.RIGHT ? 1 : -1;
            matrices.translate(invert * 0.56F, -0.52F, -0.72F);
        }
        matrices.mulPose(Axis.XP.rotationDegrees(-90.0F));
        matrices.mulPose(Axis.YP.rotationDegrees(180.0F));
        matrices.translate((arm == HumanoidArm.LEFT ? -1 : 1) / 16.0F, 2.0F / 16.0F, -10.0F / 16.0F);
    }

    @Override
    public <S extends LivingEntityRenderState> void renderStack(
            AccessoryRenderState accessoryState,
            S entityState,
            EntityModel<S> model,
            PoseStack matrices,
            SubmitNodeCollector collector,
            ItemStack stack,
            ItemStackRenderState stackRenderState,
            int light) {
        stackRenderState.submit(matrices, collector, light, OverlayTexture.NO_OVERLAY, entityState.outlineColor);
    }

    private static ItemDisplayContext displayContext(SlotPath path) {
        return arm(path) == HumanoidArm.LEFT ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    private static HumanoidArm arm(SlotPath path) {
        return path.index() % 2 == 0 ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
    }
}
