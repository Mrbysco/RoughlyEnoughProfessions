package com.mrbysco.roughlyenoughprofessions.profession;

import com.mrbysco.roughlyenoughprofessions.platform.Services;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class ProfessionEntry {
	private final VillagerProfession profession;
	private final List<ItemStack> blockStacks;

	public ProfessionEntry(VillagerProfession profession, Int2ObjectMap<ItemStack> stacks) {
		this.profession = profession;
		this.blockStacks = new LinkedList<>();
		addProfessionStacks(stacks);
	}

	public void addProfessionStacks(Int2ObjectMap<ItemStack> stackList) {
		for (int i = 0; i < stackList.size(); i++) {
			this.blockStacks.add(stackList.get(i));
		}
	}

	public VillagerProfession getProfession() {
		return profession;
	}

	public List<ItemStack> getBlockStacks() {
		return blockStacks;
	}

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
