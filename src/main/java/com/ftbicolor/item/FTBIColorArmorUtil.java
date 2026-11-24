package com.ftbicolor.item;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.platform.FTBIColorStackData;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;

public final class FTBIColorArmorUtil {
	private FTBIColorArmorUtil() {
	}

	public static boolean isQuantumArmor(ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}

		Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
		if (id == null) {
			return false;
		}

		for (Identifier quantumId : FTBIColorConstants.QUANTUM_ARMOR_ITEMS) {
			if (id.equals(quantumId)) {
				return true;
			}
		}

		return false;
	}

	public static DyeColor getColor(ItemStack stack) {
		if (!FTBIColorStackData.has(stack, FTBIColorConstants.ARMOR_COLOR_NBT)) {
			return DyeColor.WHITE;
		}

		return DyeColor.byId(FTBIColorStackData.getInt(stack, FTBIColorConstants.ARMOR_COLOR_NBT, DyeColor.WHITE.getId()));
	}

	public static boolean setColor(ItemStack stack, DyeColor color) {
		if (!isQuantumArmor(stack)) {
			return false;
		}

		if (getColor(stack) == color) {
			return false;
		}

		FTBIColorStackData.putInt(stack, FTBIColorConstants.ARMOR_COLOR_NBT, color.getId());
		return true;
	}

	public static boolean hasExplicitColor(ItemStack stack) {
		return FTBIColorStackData.has(stack, FTBIColorConstants.ARMOR_COLOR_NBT);
	}

	public static float getColorProperty(ItemStack stack) {
		return (getColor(stack).getId() + 1) / 16.0F;
	}

	public static String getArmorTexture(ItemStack stack, EquipmentSlot slot, String type) {
		boolean innerLayer = slot == EquipmentSlot.LEGS;
		String layer = "overlay".equals(type) || innerLayer ? "quantum_layer_2" : "quantum_layer_1";
		DyeColor color = getColor(stack);
		if (color == DyeColor.WHITE) {
			return "ftbic:textures/models/armor/" + layer + ".png";
		}

		return "ftbic:textures/models/armor/" + color.getName() + "/" + layer + ".png";
	}
}
