package com.mrbysco.roughlyenoughprofessions.platform;

import com.mrbysco.roughlyenoughprofessions.platform.services.IPlatformHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgePlatformHelper implements IPlatformHelper {

	@Override
	public String getVillagerID() {
		return ForgeRegistries.ENTITY_TYPES.getKey(EntityType.VILLAGER).toString();
	}

	@Override
	public ResourceLocation getProfessionID(VillagerProfession profession) {
		return ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession);
	}
}
