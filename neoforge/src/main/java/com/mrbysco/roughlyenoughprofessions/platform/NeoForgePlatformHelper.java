package com.mrbysco.roughlyenoughprofessions.platform;

import com.mrbysco.roughlyenoughprofessions.platform.services.IPlatformHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public String getVillagerID() {
		return BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.VILLAGER).toString();
	}
}
