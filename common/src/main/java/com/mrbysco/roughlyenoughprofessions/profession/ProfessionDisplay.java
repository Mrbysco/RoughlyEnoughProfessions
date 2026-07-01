package com.mrbysco.roughlyenoughprofessions.profession;

import com.mrbysco.roughlyenoughprofessions.RenderHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;

import java.util.List;

public class ProfessionDisplay {
	protected final ProfessionEntry entry;

	public ProfessionDisplay(ProfessionEntry entry) {
		this.entry = entry;
	}

	/**
	 * Get the profession name for the recipe.
	 *
	 * @return the profession name for the recipe.
	 */
	public Identifier getProfessionName() {
		return this.entry.profession().unwrapKey().orElseThrow().identifier();
	}

	/**
	 * Get the profession name for display.
	 *
	 * @return the profession name for display.
	 */
	public Component getDisplayName() {
		Identifier professionKey = getProfessionName();
		String languageKey = professionKey.toLanguageKey();
		if (languageKey.startsWith("minecraft.")) languageKey = languageKey.replace("minecraft.", "");
		return Component.translatable("entity.minecraft.villager." + languageKey);
	}

	/**
	 * Get the ItemStacks that represent the blocks in the recipe.
	 *
	 * @return a list of ItemStacks for the blocks in the recipe.
	 */
	public List<ItemStack> getBlockStacks() {
		return this.entry.blockStacks();
	}

	public void drawEntry(GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
		final Matrix3x2fStack poseStack = guiGraphics.pose();

		Villager entityVillager = entry.getVillagerEntity();
		if (entityVillager != null) {
			Vector2f position = new Vector2f(26, 62);
			position = poseStack.transformPosition(position);
			int x = Math.round(position.x);
			int y = Math.round(position.y);
			RenderHelper.renderVillager(guiGraphics, x, y, 25.0F,
					mouseX + 12,
					mouseY - 12,
					entityVillager);
		}
	}
}