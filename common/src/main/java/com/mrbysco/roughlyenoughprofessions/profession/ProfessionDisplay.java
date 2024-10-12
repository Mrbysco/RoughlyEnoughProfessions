package com.mrbysco.roughlyenoughprofessions.profession;

import com.mrbysco.roughlyenoughprofessions.RenderHelper;
import com.mrbysco.roughlyenoughprofessions.platform.Services;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ProfessionDisplay {
	protected final ProfessionEntry entry;

	public ProfessionDisplay(ProfessionEntry entry) {
		this.entry = entry;
	}

	/**
	 * Get the profession name for the recipe.
	 * @return
	 */
	public ResourceLocation getProfessionName() {
		return Services.PLATFORM.getProfessionID(this.entry.profession());
	}

	/**
	 * Get the profession name for display.
	 *
	 * @return
	 */
	public Component getDisplayName() {
		ResourceLocation professionKey = getProfessionName();
		String languageKey = professionKey.toLanguageKey();
		if (languageKey.startsWith("minecraft.")) languageKey = languageKey.replace("minecraft.", "");
		return Component.translatable("entity.minecraft.villager." + languageKey);
	}

	/**
	 * Get the ItemStacks that represent the blocks in the recipe.
	 * @return a list of ItemStacks for the blocks in the recipe.
	 */
	public List<ItemStack> getBlockStacks() {
		return this.entry.blockStacks();
	}

	public void drawEntry(GuiGraphics guiGraphics, double mouseX, double mouseY) {
		Villager entityVillager = entry.getVillagerEntity();
		if (entityVillager != null) {
			RenderHelper.renderEntity(guiGraphics, 22, 62, 25.0F,
					Mth.wrapDegrees(38 - mouseX),
					Mth.wrapDegrees(15 - mouseY),
					entityVillager);
		}
	}
}