package com.ftbicolor.item;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.platform.FTBIColorStackData;
import com.ftbicolor.platform.FTBIColorText;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class FTBIColorSprayPaintKitColor {
	private FTBIColorSprayPaintKitColor() {
	}

	@Nullable
	public static DyeColor getSelected(ItemStack stack) {
		if (!FTBIColorStackData.has(stack, FTBIColorConstants.SPRAY_KIT_SELECTED_COLOR_NBT)) {
			return null;
		}

		return DyeColor.byId(FTBIColorStackData.getInt(stack, FTBIColorConstants.SPRAY_KIT_SELECTED_COLOR_NBT, 0));
	}

	public static boolean isCurioActive(ItemStack stack) {
		return FTBIColorStackData.getBoolean(stack, FTBIColorConstants.SPRAY_KIT_CURIO_ACTIVE_NBT, true);
	}

	public static boolean toggleCurioActive(ItemStack stack) {
		boolean active = !isCurioActive(stack);
		if (active) {
			FTBIColorStackData.remove(stack, FTBIColorConstants.SPRAY_KIT_CURIO_ACTIVE_NBT);
		} else {
			FTBIColorStackData.putBoolean(stack, FTBIColorConstants.SPRAY_KIT_CURIO_ACTIVE_NBT, false);
		}
		return active;
	}

	public static void setSelected(ItemStack stack, @Nullable DyeColor color) {
		if (color == null) {
			FTBIColorStackData.remove(stack, FTBIColorConstants.SPRAY_KIT_SELECTED_COLOR_NBT);
			return;
		}

		FTBIColorStackData.putInt(stack, FTBIColorConstants.SPRAY_KIT_SELECTED_COLOR_NBT, color.getId());
	}

	public static Component selectedColorName(ItemStack stack) {
		DyeColor color = getSelected(stack);
		if (color == null) {
			return FTBIColorText.translatable("item.ftbicolor.spray_paint_kit.no_color");
		}

		return FTBIColorText.translatable("color.minecraft." + color.getName());
	}
}
