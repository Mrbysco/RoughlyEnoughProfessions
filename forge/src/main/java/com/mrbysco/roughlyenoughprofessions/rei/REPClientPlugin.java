package com.mrbysco.roughlyenoughprofessions.rei;

import com.mrbysco.roughlyenoughprofessions.Constants;
import com.mrbysco.roughlyenoughprofessions.compat.CompatibilityHelper;
import com.mrbysco.roughlyenoughprofessions.platform.Services;
import com.mrbysco.roughlyenoughprofessions.profession.ProfessionEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
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
		List<ProfessionEntry> entries = new LinkedList<>();
		for (VillagerProfession profession : ForgeRegistries.PROFESSIONS) {
			List<ItemStack> stacks = new LinkedList<>();
			List<ResourceLocation> knownItems = new LinkedList<>();
			PoiType poiType = profession.getJobPoiType();
			for (BlockState state : poiType.matchingStates) {
				Block block = ForgeRegistries.BLOCKS.getValue(state.getBlock().getRegistryName());
				if (block != null) {
					ItemStack stack = CompatibilityHelper.compatibilityCheck(new ItemStack(block), Services.PLATFORM.getProfessionID(profession));
					ResourceLocation location = stack.getItem().getRegistryName();
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
		entries.forEach((entry) -> {
			registry.add(new ProfessionDisplayForge(entry));
		});
	}
}