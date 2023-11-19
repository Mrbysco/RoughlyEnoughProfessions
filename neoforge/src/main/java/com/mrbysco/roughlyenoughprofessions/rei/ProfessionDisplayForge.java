package com.mrbysco.roughlyenoughprofessions.rei;

import com.mrbysco.roughlyenoughprofessions.profession.ProfessionDisplay;
import com.mrbysco.roughlyenoughprofessions.profession.ProfessionEntry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.Collections;
import java.util.List;

public class ProfessionDisplayForge extends ProfessionDisplay implements Display {
	private EntryIngredient entryStacks;

	public ProfessionDisplayForge(ProfessionEntry entry) {
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
}