package com.ftbicolor.platform;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.DyeColor;

public final class FTBIColorDye {
	private FTBIColorDye() {
	}

	public static DyeColor fromVanillaCan(boolean dark) {
		return dark ? DyeColor.BLACK : DyeColor.WHITE;
	}

	public static int opaqueRgb(DyeColor color) {
		return color.getTextureDiffuseColor();
	}

	public static ChatFormatting chatFormatting(DyeColor color) {
		return switch (color) {
			case ORANGE, BROWN -> ChatFormatting.GOLD;
			case MAGENTA, PINK -> ChatFormatting.LIGHT_PURPLE;
			case LIGHT_BLUE -> ChatFormatting.AQUA;
			case YELLOW -> ChatFormatting.YELLOW;
			case LIME -> ChatFormatting.GREEN;
			case GRAY -> ChatFormatting.DARK_GRAY;
			case LIGHT_GRAY, BLACK -> ChatFormatting.GRAY;
			case CYAN -> ChatFormatting.DARK_AQUA;
			case PURPLE -> ChatFormatting.DARK_PURPLE;
			case BLUE -> ChatFormatting.BLUE;
			case GREEN -> ChatFormatting.DARK_GREEN;
			case RED -> ChatFormatting.RED;
			case WHITE -> ChatFormatting.WHITE;
		};
	}
}
