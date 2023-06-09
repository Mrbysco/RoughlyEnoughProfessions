package com.mrbysco.roughlyenoughprofessions.profession;

import com.mrbysco.roughlyenoughprofessions.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public record ProfessionEntry(VillagerProfession profession, List<ItemStack> blockStacks) {

	@Nullable
	public Villager getVillagerEntity() {
		CompoundTag nbt = new CompoundTag();
		nbt.putString("id", Services.PLATFORM.getVillagerID());
		Minecraft mc = Minecraft.getInstance();
		Level level = mc.hasSingleplayerServer() && mc.getSingleplayerServer() != null ? mc.getSingleplayerServer().getAllLevels().iterator().next() : mc.level;
		if (level != null) {
			Villager villager = (Villager) EntityType.loadEntityRecursive(nbt, level, Function.identity());
			if (villager != null) {
				villager.setVillagerData(villager.getVillagerData().setProfession(this.profession));
				return villager;
			}
		}
		return null;
	}
}
