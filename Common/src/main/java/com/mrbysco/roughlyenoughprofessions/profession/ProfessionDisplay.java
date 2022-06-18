package com.mrbysco.roughlyenoughprofessions.profession;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.roughlyenoughprofessions.RenderHelper;
import com.mrbysco.roughlyenoughprofessions.platform.Services;
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

	public ResourceLocation getProfessionName() {
		return Services.PLATFORM.getProfessionID(this.entry.profession());
	}

	public List<ItemStack> getBlockStacks() {
		return this.entry.blockStacks();
	}

	public void drawEntry(PoseStack poseStack, double mouseX, double mouseY) {
		Villager entityVillager = entry.getVillagerEntity();
		if (entityVillager != null) {
			RenderHelper.renderEntity(poseStack, 22, 62, 25.0F,
					Mth.wrapDegrees(38 - mouseX),
					Mth.wrapDegrees(80 - mouseY),
					entityVillager);
		}
	}
}