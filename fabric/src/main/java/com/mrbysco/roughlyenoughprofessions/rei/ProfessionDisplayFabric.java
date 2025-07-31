package com.mrbysco.roughlyenoughprofessions.rei;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrbysco.roughlyenoughprofessions.profession.ProfessionDisplay;
import com.mrbysco.roughlyenoughprofessions.profession.ProfessionEntry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProfessionDisplayFabric extends ProfessionDisplay implements Display {
	public static final DisplaySerializer<ProfessionDisplayFabric> SERIALIZER = DisplaySerializer.of(
			RecordCodecBuilder.mapCodec(instance -> instance.group(
					ProfessionEntry.CODEC.fieldOf("entryStacks").forGetter(d -> d.entry)
			).apply(instance, ProfessionDisplayFabric::new)),
			StreamCodec.composite(
					ProfessionEntry.STREAM_CODEC,
					d -> d.entry,
					ProfessionDisplayFabric::new
			));
	private EntryIngredient entryStacks;

	public ProfessionDisplayFabric(ProfessionEntry entry) {
		super(entry);
		this.entryStacks = EntryIngredients.of(VanillaEntryTypes.ITEM, entry.blockStacks());
	}

	@Override
	public List<EntryIngredient> getInputEntries() {
		return Collections.singletonList(entryStacks);
	}

	@Override
	public List<EntryIngredient> getOutputEntries() {
		return Collections.singletonList(entryStacks);
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return REPClientPlugin.PROFESSION;
	}

	@Override
	public Optional<ResourceLocation> getDisplayLocation() {
		return Optional.empty();
	}

	@Override
	public DisplaySerializer<? extends Display> getSerializer() {
		return SERIALIZER;
	}
}