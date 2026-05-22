package com.ftbicolor.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class FTBIColorSprayPaintKitUtil {
	private FTBIColorSprayPaintKitUtil() {
	}

	public static boolean shouldOpenWheel(@Nullable Player player, ItemStack stack, boolean controlDown) {
		return player != null && player.isShiftKeyDown() && controlDown;
	}

	public static void applyColor(Player player, ItemStack stack, FTBIColorSprayPaintKitType kitType, @Nullable DyeColor color) {
		if (color != null && !kitType.supports(color)) {
			return;
		}

		FTBIColorSprayPaintKitColor.setSelected(stack, color);
	}
}
