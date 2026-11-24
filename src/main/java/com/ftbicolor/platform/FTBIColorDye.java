package com.ftbicolor.platform;

import net.minecraft.ChatFormatting;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;

public final class FTBIColorDye {
	private FTBIColorDye() {
	}

	public static DyeColor fromVanillaCan(boolean dark) {
		return dark ? DyeColor.BLACK : DyeColor.WHITE;
	}

	public static int opaqueRgb(DyeColor color) {
		float[] channels = color.getTextureDiffuseColors();
		int r = Mth.clamp((int) (channels[0] * 255.0F), 0, 255);
		int g = Mth.clamp((int) (channels[1] * 255.0F), 0, 255);
		int b = Mth.clamp((int) (channels[2] * 255.0F), 0, 255);
		return 0xFF000000 | (r << 16) | (g << 8) | b;
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
