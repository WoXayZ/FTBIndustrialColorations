package com.ftbicolor.client;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.Map;

public final class FTBIColorQuarryTextures {
	private static final Map<DyeColor, RenderType> MODELS = new EnumMap<>(DyeColor.class);

	private FTBIColorQuarryTextures() {
	}

	public static RenderType model(DyeColor color) {
		return MODELS.computeIfAbsent(color, FTBIColorQuarryTextures::create);
	}

	private static RenderType create(DyeColor color) {
		String path;
		if (color == DyeColor.WHITE) {
			path = "textures/block/quarry_model_light.png";
		} else if (color == DyeColor.BLACK) {
			path = "textures/block/quarry_model_dark.png";
		} else {
			path = "textures/block/quarry/" + color.getSerializedName() + ".png";
		}
		return RenderTypes.entityCutout(Identifier.fromNamespaceAndPath("ftbic", path));
	}
}
