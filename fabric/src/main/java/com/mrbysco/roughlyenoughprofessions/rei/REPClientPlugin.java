package com.mrbysco.roughlyenoughprofessions.rei;

import com.mrbysco.roughlyenoughprofessions.Constants;
import com.mrbysco.roughlyenoughprofessions.profession.ProfessionEntry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.registries.BuiltInRegistries;
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
		List<ResourceKey<VillagerProfession>> professions = BuiltInRegistries.VILLAGER_PROFESSION.registryKeySet().stream().toList();
		for (ResourceKey<VillagerProfession> professionKey : professions) {
			if (professionKey == VillagerProfession.NONE) continue;
			List<ItemStack> stacks = new LinkedList<>();
			List<ResourceLocation> knownItems = new LinkedList<>();
			List<PoiType> types = BuiltInRegistries.POINT_OF_INTEREST_TYPE.stream().toList();
			VillagerProfession profession = BuiltInRegistries.VILLAGER_PROFESSION.getValue(professionKey);
			if (profession == null) continue;
			for (PoiType poiType : types) {
				if (profession.acquirableJobSite().test(BuiltInRegistries.POINT_OF_INTEREST_TYPE.wrapAsHolder(poiType))) {
					for (BlockState state : poiType.matchingStates()) {
						Block block = state.getBlock(); //Registry replacements are gone?
						if (block != null) {
							ItemStack stack = new ItemStack(block);
							ResourceLocation location = BuiltInRegistries.ITEM.getKey(stack.getItem());
							if (!stack.isEmpty() && !knownItems.contains(location)) {
								stacks.add(stack);
								knownItems.add(location);
							}
						}
					}
				}
			}
			if (!stacks.isEmpty()) {
				entries.add(new ProfessionDisplayFabric(new ProfessionEntry(BuiltInRegistries.VILLAGER_PROFESSION.get(professionKey).orElse(null), stacks)));
			}
		}

		entries.forEach(registry::add);
	}
}