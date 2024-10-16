package com.mrbysco.roughlyenoughprofessions;

import com.mrbysco.roughlyenoughprofessions.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class VillagerCache {
	private static Villager cachedVillager;

	@Nullable
	public static Villager getVillagerEntity(VillagerProfession profession) {
		if (cachedVillager == null) {
			CompoundTag nbt = new CompoundTag();
			nbt.putString("id", Services.PLATFORM.getVillagerID());
			Minecraft mc = Minecraft.getInstance();
			ClientLevel level = Minecraft.getInstance().level;
			if (level != null) {
				Villager villager = (Villager) EntityType.loadEntityRecursive(nbt, level, Function.identity());
				if (villager != null) {
					cachedVillager = villager;
				}
			}
		} else {
			cachedVillager.setVillagerData(cachedVillager.getVillagerData().setProfession(profession));
			return cachedVillager;
		}

		return null;
	}

}
