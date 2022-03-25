package com.mrbysco.roughlyenoughprofessions.platform.services;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;

public interface IPlatformHelper {

	/**
	 * Get's the ID of a given villager
	 *
	 * @return The id of the villager EntityType
	 */
	String getVillagerID();

	/**
	 * Get's the ID of a given profession
	 *
	 * @return The id of the profession
	 */
	ResourceLocation getProfessionID(VillagerProfession profession);
}
