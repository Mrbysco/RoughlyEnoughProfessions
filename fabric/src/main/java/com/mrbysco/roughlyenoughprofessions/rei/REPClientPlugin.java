package com.mrbysco.roughlyenoughprofessions.rei;

import com.mrbysco.roughlyenoughprofessions.Constants;
import com.mrbysco.roughlyenoughprofessions.mixin.PoiTypeAccessor;
import com.mrbysco.roughlyenoughprofessions.profession.ProfessionEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class REPClientPlugin implements REIClientPlugin {
	public static final CategoryIdentifier<ProfessionDisplayFabric> PROFESSION = CategoryIdentifier.of(Constants.MOD_ID, "plugins/profession");

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new ProfessionCategory());

		registry.addWorkstations(PROFESSION, EntryStacks.of(Items.EMERALD), EntryStacks.of(Items.VILLAGER_SPAWN_EGG));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		List<ProfessionEntry> entries = new LinkedList<>();
		for (VillagerProfession profession : Registry.VILLAGER_PROFESSION) {
			List<ItemStack> stacks = new LinkedList<>();
			List<ResourceLocation> knownItems = new LinkedList<>();
			PoiType poiType = profession.getJobPoiType();
			Set<BlockState> matchingStates = ((PoiTypeAccessor) poiType).getMatchingStates();
			for (BlockState state : matchingStates) {
				Block block = Registry.BLOCK.get(Registry.BLOCK.getKey(state.getBlock()));
				if (block != null) {
					ItemStack stack = new ItemStack(block);
					ResourceLocation location = Registry.ITEM.getKey(stack.getItem());
					if (!stack.isEmpty() && !knownItems.contains(location)) {
						stacks.add(stack);
						knownItems.add(location);
					}
				}
			}
			if (!stacks.isEmpty()) {
				Int2ObjectMap<ItemStack> map = new Int2ObjectOpenHashMap<>();
				for (int i = 0; i < stacks.size(); i++) {
					map.put(i, stacks.get(i));
				}
				entries.add(new ProfessionEntry(profession, map));
			}
		}
		entries.forEach((entry) -> registry.add(new ProfessionDisplayFabric(entry)));
	}
}