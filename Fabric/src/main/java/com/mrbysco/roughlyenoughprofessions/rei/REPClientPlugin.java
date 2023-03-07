package com.mrbysco.roughlyenoughprofessions.rei;

import com.mrbysco.roughlyenoughprofessions.Constants;
import com.mrbysco.roughlyenoughprofessions.profession.ProfessionEntry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedList;
import java.util.List;

public class REPClientPlugin implements REIClientPlugin {
	public static final CategoryIdentifier<ProfessionDisplayFabric> PROFESSION = CategoryIdentifier.of(Constants.MOD_ID, "plugins/profession");

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new ProfessionCategory());

		registry.addWorkstations(PROFESSION, EntryStacks.of(Items.EMERALD), EntryStacks.of(Items.VILLAGER_SPAWN_EGG));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		List<ProfessionDisplayFabric> entries = new LinkedList<>();
		for (VillagerProfession profession : Registry.VILLAGER_PROFESSION) {
			if (profession == VillagerProfession.NONE) {
				continue;
			}
			List<ItemStack> stacks = new LinkedList<>();
			List<ResourceLocation> knownItems = new LinkedList<>();
			for (PoiType poiType : Registry.POINT_OF_INTEREST_TYPE) {
				ResourceKey<PoiType> key = Registry.POINT_OF_INTEREST_TYPE.getResourceKey(poiType).orElse(null);
				if (key != null && profession.acquirableJobSite().test(Registry.POINT_OF_INTEREST_TYPE.getHolder(key).orElse(null))) {
					for (BlockState state : poiType.matchingStates()) {
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
				}
			}
			if (!stacks.isEmpty()) {
				entries.add(new ProfessionDisplayFabric(new ProfessionEntry(profession, stacks)));
			}
		}

		entries.forEach(registry::add);
	}
}