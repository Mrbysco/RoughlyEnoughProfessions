package com.mrbysco.roughlyenoughprofessions;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.villager.Villager;
import org.joml.Matrix3x2fStack;

/**
 * A helper class to render the Villager entity on the screen
 */
public class RenderHelper {
	/**
	 * Render the Villager entity on the screen
	 *
	 * @param guiGraphics The GuiGraphics instance
	 * @param x           The x position
	 * @param y           The y position
	 * @param scale       The scale of the entity
	 * @param mouseX      The mouse X position
	 * @param mouseY      The mouse Y position
	 * @param villager    The Villager entity to render
	 */
	public static void renderVillager(GuiGraphicsExtractor guiGraphics, int x, int y, double scale, double mouseX, double mouseY, Villager villager) {
		if (villager.level() == null) return;

		int startX = x - 60;
		int startY = y - 60;
		int endX = x + 60;
		int endY = y + 60;

		Matrix3x2fStack poseStack = guiGraphics.pose();
		poseStack.pushMatrix();
		poseStack.scale((float) scale, (float) scale);

		poseStack.translate(0.0F, (float) villager.getVehicleAttachmentPoint(villager).y());

		double mouseScale = scale / 4;
		int adjustedMouseX = Mth.ceil(mouseX * mouseScale);
		int adjustedMouseY = Mth.ceil(mouseY * mouseScale);

		poseStack.translate(0.0F, (float) villager.getVehicleAttachmentPoint(villager).y());

		InventoryScreen.extractEntityInInventoryFollowsMouse(
				guiGraphics, startX, startY, endX, endY, (int) 20, -1, adjustedMouseX, adjustedMouseY, villager);

		poseStack.popMatrix();
	}
}
