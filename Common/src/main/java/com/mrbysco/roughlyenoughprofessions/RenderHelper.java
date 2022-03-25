package com.mrbysco.roughlyenoughprofessions;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.LivingEntity;

public class RenderHelper {
	public static void renderEntity(PoseStack poseStack, int x, int y, double scale, double yaw, double pitch, LivingEntity livingEntity) {
		if (livingEntity.level == null) livingEntity.level = Minecraft.getInstance().level;
		poseStack.pushPose();
		poseStack.translate((float) x, (float) y, 50f);
		poseStack.scale((float) scale, (float) scale, (float) scale);
		poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
		// Rotate entity
		poseStack.mulPose(Vector3f.XP.rotationDegrees(((float) Math.atan((-40 / 40.0F))) * 10.0F));

		livingEntity.yBodyRot = (float) -(yaw / 40.F) * 20.0F;
		livingEntity.setYRot((float) -(yaw / 40.F) * 20.0F);
		livingEntity.yHeadRot = livingEntity.getYRot();
		livingEntity.yHeadRotO = livingEntity.getYRot();

		poseStack.translate(0.0F, livingEntity.getMyRidingOffset(), 0.0F);
		EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
		entityRenderDispatcher.overrideCameraOrientation(Quaternion.ONE);
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
