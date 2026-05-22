package com.ftbicolor.item;

import net.minecraft.world.item.DyeColor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum FTBIColorSprayPaintKitType {
	PRIMARY(
			"colored_spray_paint_kit_primary",
			DyeColor.WHITE,
			DyeColor.ORANGE,
			DyeColor.MAGENTA,
			DyeColor.LIGHT_BLUE,
			DyeColor.YELLOW,
			DyeColor.LIME,
			DyeColor.PINK,
			DyeColor.GRAY
	),
	SECONDARY(
			"colored_spray_paint_kit_secondary",
			DyeColor.LIGHT_GRAY,
			DyeColor.CYAN,
			DyeColor.PURPLE,
			DyeColor.BLUE,
			DyeColor.BROWN,
			DyeColor.GREEN,
			DyeColor.RED,
			DyeColor.BLACK
	),
	FULL(
			"colored_spray_paint_selector",
			DyeColor.WHITE,
			DyeColor.ORANGE,
			DyeColor.MAGENTA,
			DyeColor.LIGHT_BLUE,
			DyeColor.YELLOW,
			DyeColor.LIME,
			DyeColor.PINK,
			DyeColor.GRAY,
			DyeColor.LIGHT_GRAY,
			DyeColor.CYAN,
			DyeColor.PURPLE,
			DyeColor.BLUE,
			DyeColor.BROWN,
			DyeColor.GREEN,
			DyeColor.RED,
			DyeColor.BLACK
	);

	private final String registryName;
	private final List<DyeColor> colors;

	FTBIColorSprayPaintKitType(String registryName, DyeColor... colors) {
		this.registryName = registryName;
		this.colors = Collections.unmodifiableList(Arrays.asList(colors));
	}

	public String registryName() {
		return registryName;
	}

	public List<DyeColor> colors() {
		return colors;
	}

	public boolean supports(DyeColor color) {
		return colors.contains(color);
	}
}
