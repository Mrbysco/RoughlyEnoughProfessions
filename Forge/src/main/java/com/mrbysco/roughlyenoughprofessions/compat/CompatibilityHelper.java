package com.mrbysco.roughlyenoughprofessions.compat;

import me.shedaniel.rei.impl.client.gui.screen.DefaultDisplayViewingScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

public class CompatibilityHelper {
	public static ItemStack compatibilityCheck(ItemStack stack, @Nullable ResourceLocation profession) {
		if (profession != null) {
			if (profession.equals(new ResourceLocation("immersiveengineering", "outfitter"))) {
				CompoundTag tag = stack.hasTag() ? stack.getTag() : new CompoundTag();
				tag.putBoolean("REP_outfitter", true);
				stack.setTag(tag);
				return stack;
			}
		}
		return stack;
	}

	@SubscribeEvent
	public static void handleTooltips(ItemTooltipEvent event) {
		if (Minecraft.getInstance().screen instanceof DefaultDisplayViewingScreen) {
			ItemStack stack = event.getItemStack();
			if (stack.hasTag() && stack.getTag().getBoolean("REP_outfitter")) {
				event.getToolTip().add(Component.literal("Needs to have a shader applied").withStyle(ChatFormatting.GOLD));
			}
		}
	}
}