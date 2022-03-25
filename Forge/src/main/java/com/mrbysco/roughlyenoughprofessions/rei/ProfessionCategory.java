package com.mrbysco.roughlyenoughprofessions.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ProfessionCategory implements DisplayCategory<ProfessionDisplayForge> {

	@Override
	public Renderer getIcon() {
		return EntryStacks.of(Items.EMERALD);
	}

	@Override
	public Component getTitle() {
		return new TranslatableComponent("roughlyenoughprofessions.professions.title");
	}

	@Override
	public CategoryIdentifier<? extends ProfessionDisplayForge> getCategoryIdentifier() {
		return REPClientPlugin.PROFESSION;
	}

	@Override
	public List<Widget> setupDisplay(ProfessionDisplayForge display, Rectangle bounds) {
		Point startPoint = new Point(bounds.getCenterX() - 36, bounds.getCenterY());
		List<Widget> widgets = new ArrayList<>();
		widgets.add(Widgets.createRecipeBase(bounds));
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 24, startPoint.y)));
		widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((drawableHelper, poseStack, mouseX, mouseY, v) -> {
			// Draw entity name
			poseStack.pushPose();
			poseStack.translate(1, 0, 0);
			Font font = Minecraft.getInstance().font;
			String text = Screen.hasShiftDown() ? display.getProfessionName().toString() : display.getProfessionName().getPath();
			if (font.width(text) > 122) {
				poseStack.scale(0.75F, 0.75F, 0.75F);
			}
			font.draw(poseStack, text, 0, 0, 8);
			poseStack.popPose();
		}), bounds.x + 3, bounds.y + 3, 0));
		widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((drawableHelper, poseStack, mouseX, mouseY, v) -> {
			// Draw entity
			display.drawEntry(poseStack, mouseX, mouseY);
		}), bounds.x + 56, bounds.y + 2, 0));
		widgets.add(Widgets.createSlot(new Point(startPoint)).entries(display.getInputEntries().get(0)).markInput());

		return widgets;
	}

	@Override
	public int getDisplayWidth(ProfessionDisplayForge display) {
		return 100;
	}

	@Override
	public int getDisplayHeight() {
		return 70;
	}
}
