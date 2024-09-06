package com.mrbysco.roughlyenoughprofessions.profession;

import com.mrbysco.roughlyenoughprofessions.VillagerCache;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ProfessionEntry(VillagerProfession profession, List<ItemStack> blockStacks) {

	@Nullable
	public Villager getVillagerEntity() {
		return VillagerCache.getVillagerEntity(this.profession);
	}
}
