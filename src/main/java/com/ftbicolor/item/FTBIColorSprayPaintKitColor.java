package com.ftbicolor.item;

import com.ftbicolor.FTBIColorConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class FTBIColorSprayPaintKitColor {
	private FTBIColorSprayPaintKitColor() {
	}

	@Nullable
	public static DyeColor getSelected(ItemStack stack) {
		if (!stack.hasTag()) {
			return null;
		}

		CompoundTag tag = stack.getTag();
		if (tag == null || !tag.contains(FTBIColorConstants.SPRAY_KIT_SELECTED_COLOR_NBT)) {
			return null;
		}

		return DyeColor.byId(tag.getInt(FTBIColorConstants.SPRAY_KIT_SELECTED_COLOR_NBT));
	}

	public static void setSelected(ItemStack stack, @Nullable DyeColor color) {
		if (color == null) {
			if (stack.hasTag()) {
				CompoundTag tag = stack.getTag();
				if (tag != null) {
					tag.remove(FTBIColorConstants.SPRAY_KIT_SELECTED_COLOR_NBT);
					if (tag.isEmpty()) {
						stack.setTag(null);
					}
				}
			}
			return;
		}

		stack.getOrCreateTag().putInt(FTBIColorConstants.SPRAY_KIT_SELECTED_COLOR_NBT, color.getId());
	}

	public static Component selectedColorName(ItemStack stack) {
		DyeColor color = getSelected(stack);
		if (color == null) {
			return new TranslatableComponent("item.ftbicolor.spray_paint_kit.no_color");
		}

		return new TranslatableComponent("color.minecraft." + color.getName());
	}
}
