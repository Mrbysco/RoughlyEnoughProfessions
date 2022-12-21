package com.mrbysco.roughlyenoughprofessions;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;

public class RenderHelper {
	public static void renderEntity(PoseStack poseStack, int x, int y, double scale, double yaw, double pitch, LivingEntity livingEntity) {
		if (livingEntity.level == null) livingEntity.level = Minecraft.getInstance().level;
		poseStack.pushPose();
		poseStack.translate((float) x, (float) y, 50f);
		poseStack.scale((float) scale, (float) scale, (float) scale);
		poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
		// Rotate entity
		poseStack.mulPose(Axis.XP.rotationDegrees(((float) Math.atan((-40 / 40.0F))) * 10.0F));

		livingEntity.yBodyRot = (float) -(yaw / 40.F) * 20.0F;
		livingEntity.setYRot((float) -(yaw / 40.F) * 20.0F);
		livingEntity.yHeadRot = livingEntity.getYRot();
		livingEntity.yHeadRotO = livingEntity.getYRot();

		poseStack.translate(0.0F, livingEntity.getMyRidingOffset(), 0.0F);
		EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
		Quaternionf quaternionf1 = (new Quaternionf()).rotateX(((float) pitch) * 20.0F * ((float) Math.PI / 180F));
		quaternionf1.conjugate();
		entityRenderDispatcher.overrideCameraOrientation(quaternionf1);
		entityRenderDispatcher.setRenderShadow(false);
		final MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
		RenderSystem.runAsFancy(() -> {
			entityRenderDispatcher.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack, bufferSource, 15728880);
		});
		bufferSource.endBatch();
		entityRenderDispatcher.setRenderShadow(true);
		poseStack.popPose();
	}
}
