package com.ftbicolor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

public final class FTBIColorConstants {
	public static final String MOD_ID = "ftbicolor";
	public static final String FTBIC_MOD_ID = "ftbic";

	public static final String ARMOR_COLOR_NBT = "Color";
	public static final String SPRAY_KIT_SELECTED_COLOR_NBT = "SelectedColor";
	public static final String PLAYER_USED_SPRAY_CANS_NBT = MOD_ID + ":used_spray_cans";

	public static final String ITEM_PROPERTY_COLOR = "color";
	public static final String ITEM_PROPERTY_HAS_COLOR = "has_color";

	public static final ResourceLocation FTBIC_LIGHT_SPRAY_CAN = ftbicItem("light_spray_paint_can");
	public static final ResourceLocation FTBIC_DARK_SPRAY_CAN = ftbicItem("dark_spray_paint_can");

	public static final ResourceLocation ADVANCEMENT_ROOT = ftbicAdvancement("ftbicolor/root");
	public static final ResourceLocation ADVANCEMENT_COLORFUL_WORLD = ftbicAdvancement("ftbicolor/colorful_world");
	public static final ResourceLocation ADVANCEMENT_QUANTUM_COLORATION = ftbicAdvancement("ftbicolor/quantum_coloration");
	public static final ResourceLocation ADVANCEMENT_SO_MANY_COLORS = ftbicAdvancement("ftbicolor/so_many_colors");
	public static final ResourceLocation ADVANCEMENT_UNLOCK_KITS = ftbicAdvancement("ftbicolor/recipes/unlock_kits");
	public static final ResourceLocation ADVANCEMENT_UNLOCK_SELECTOR = ftbicAdvancement("ftbicolor/recipes/unlock_selector");

	public static final ResourceLocation RECIPE_KIT_PRIMARY = modRecipe("crafting/colored_spray_paint_kit_primary");
	public static final ResourceLocation RECIPE_KIT_SECONDARY = modRecipe("crafting/colored_spray_paint_kit_secondary");
	public static final ResourceLocation RECIPE_SELECTOR = modRecipe("crafting/colored_spray_paint_selector");

	public static final ResourceLocation ITEM_PROPERTY_COLOR_ID = modResource(ITEM_PROPERTY_COLOR);
	public static final ResourceLocation ITEM_PROPERTY_HAS_COLOR_ID = modResource(ITEM_PROPERTY_HAS_COLOR);

	public static final ResourceLocation QUANTUM_BOOTS = ftbicItem("quantum_boots");
	public static final ResourceLocation QUANTUM_CHESTPLATE = ftbicItem("quantum_chestplate");
	public static final ResourceLocation QUANTUM_HELMET = ftbicItem("quantum_helmet");
	public static final ResourceLocation QUANTUM_LEGGINGS = ftbicItem("quantum_leggings");

	public static final ResourceLocation[] QUANTUM_ARMOR_ITEMS = {
			QUANTUM_BOOTS,
			QUANTUM_CHESTPLATE,
			QUANTUM_HELMET,
			QUANTUM_LEGGINGS,
	};

	public static final EquipmentSlot[] ARMOR_EQUIPMENT_SLOTS = {
			EquipmentSlot.HEAD,
			EquipmentSlot.CHEST,
			EquipmentSlot.LEGS,
			EquipmentSlot.FEET,
	};

	private FTBIColorConstants() {
	}

	public static ResourceLocation modResource(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static ResourceLocation modRecipe(String path) {
		return modResource(path);
	}

	public static ResourceLocation ftbicItem(String path) {
		return new ResourceLocation(FTBIC_MOD_ID, path);
	}

	public static ResourceLocation ftbicAdvancement(String path) {
		return new ResourceLocation(FTBIC_MOD_ID, path);
	}
}
