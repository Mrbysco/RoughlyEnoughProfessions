package com.mrbysco.roughlyenoughprofessions.rei;

import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ProfessionCategory implements DisplayCategory<ProfessionDisplayNeoForge> {

	@Override
	public Renderer getIcon() {
		return EntryStacks.of(Items.EMERALD);
	}

	@Override
	public Component getTitle() {
		return Component.translatable("roughlyenoughprofessions.professions.title");
	}

	@Override
	public CategoryIdentifier<? extends ProfessionDisplayNeoForge> getCategoryIdentifier() {
		return REPClientPlugin.PROFESSION;
	}

	@Override
	public List<Widget> setupDisplay(ProfessionDisplayNeoForge display, Rectangle bounds) {
		Point startPoint = new Point(bounds.getCenterX() - 36, bounds.getCenterY());
		List<Widget> widgets = new ArrayList<>();
		widgets.add(Widgets.createRecipeBase(bounds));
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 24, startPoint.y)));
		widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, v) -> {
			// Draw entity name
			PoseStack poseStack = guiGraphics.pose();
			poseStack.pushPose();
			poseStack.translate(1, 0, 0);
			Font font = Minecraft.getInstance().font;
			String text = Screen.hasShiftDown() ? display.getProfessionName().toString() : display.getDisplayName().getString();
			if (font.width(text) > 122) {
				poseStack.scale(0.75F, 0.75F, 0.75F);
			}
			guiGraphics.drawString(font, text, 0, 0, 8, false);
			poseStack.popPose();
		}), bounds.x + 3, bounds.y + 3, 0));
		widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, v) -> {
			// Draw entity
			display.drawEntry(guiGraphics, mouseX, mouseY);
		}), bounds.x + 56, bounds.y + 2, 0));
		widgets.add(Widgets.createSlot(new Point(startPoint)).entries(display.getInputEntries().get(0)).markInput());

		return widgets;
	}

	@Override
	public int getDisplayWidth(ProfessionDisplayNeoForge display) {
		return 100;
	}

	@Override
	public int getDisplayHeight() {
		return 70;
	}
}
