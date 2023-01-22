package com.mrbysco.roughlyenoughprofessions.rei;

import com.mrbysco.roughlyenoughprofessions.Constants;
import com.mrbysco.roughlyenoughprofessions.compat.CompatibilityHelper;
import com.mrbysco.roughlyenoughprofessions.profession.ProfessionEntry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.forge.REIPluginDedicatedServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedList;
import java.util.List;

@REIPluginClient
public class REPClientPlugin implements REIClientPlugin {
	public static final CategoryIdentifier<ProfessionDisplayForge> PROFESSION = CategoryIdentifier.of(Constants.MOD_ID, "plugins/profession");

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(new ProfessionCategory());

		registry.addWorkstations(PROFESSION, EntryStacks.of(Items.EMERALD), EntryStacks.of(Items.VILLAGER_SPAWN_EGG));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		List<ProfessionDisplayForge> entries = new LinkedList<>();
		for (VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS) {
			if (profession == VillagerProfession.NONE) {
				continue;
			}
			for (PoiType poiType : ForgeRegistries.POI_TYPES.getValues()) {
				if (profession.acquirableJobSite().test(ForgeRegistries.POI_TYPES.getHolder(poiType).orElse(null))) {
					List<ItemStack> stacks = new LinkedList<>();
					List<ResourceLocation> knownItems = new LinkedList<>();
					for (BlockState state : poiType.matchingStates()) {
						Block block = ForgeRegistries.BLOCKS.getValue(ForgeRegistries.BLOCKS.getKey(state.getBlock()));
						if (block != null) {
							ItemStack stack = CompatibilityHelper.compatibilityCheck(new ItemStack(block), ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession));
							ResourceLocation location = ForgeRegistries.ITEMS.getKey(stack.getItem());
							if (!stack.isEmpty() && !knownItems.contains(location)) {
								stacks.add(stack);
								knownItems.add(location);
							}
						}
					}
					if (!stacks.isEmpty()) {
						entries.add(new ProfessionDisplayForge(new ProfessionEntry(profession, stacks)));
					}
				}
			}
		}

		entries.forEach(registry::add);
	}
}