package com.mrbysco.roughlyenoughprofessions.platform;

import com.mrbysco.roughlyenoughprofessions.platform.services.IPlatformHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerProfession;

public class FabricPlatformHelper implements IPlatformHelper {

	@Override
	public String getVillagerID() {
		return Registry.ENTITY_TYPE.getKey(EntityType.VILLAGER).toString();
	}

	@Override
	public ResourceLocation getProfessionID(VillagerProfession profession) {
		return Registry.VILLAGER_PROFESSION.getKey(profession);
	}
}
