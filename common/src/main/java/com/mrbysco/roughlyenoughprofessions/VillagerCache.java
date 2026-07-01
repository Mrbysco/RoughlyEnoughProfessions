package com.mrbysco.roughlyenoughprofessions;

import com.mrbysco.roughlyenoughprofessions.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import org.jetbrains.annotations.Nullable;

public class VillagerCache {
	private static Villager cachedVillager;

	@Nullable
	public static Villager getVillagerEntity(Holder<VillagerProfession> professionHolder) {
		if (cachedVillager == null) {
			CompoundTag nbt = new CompoundTag();
			nbt.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(EntityTypes.VILLAGER).toString());
			Minecraft mc = Minecraft.getInstance();
			ClientLevel level = mc.level;
			if (level != null) {
				Villager villager = (Villager) EntityType.loadEntityRecursive(EntityTypes.VILLAGER, nbt, level, EntitySpawnReason.LOAD, entity -> entity);
				if (villager != null) {
					villager.setId(-1); // Stop entity ID access before assignment crash
					cachedVillager = villager;
				}
			}
		} else {
			cachedVillager.setVillagerData(cachedVillager.getVillagerData().withProfession(professionHolder));
			return cachedVillager;
		}

		return null;
	}

}
