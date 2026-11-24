package com.ftbicolor.platform;

import com.ftbicolor.FTBIColorConstants;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public final class FTBIColorStackData {
	private FTBIColorStackData() {
	}

	public static boolean has(ItemStack stack, String key) {
		DataComponentType<?> type = type(key);
		return type != null && stack.has(type);
	}

	public static int getInt(ItemStack stack, String key, int fallback) {
		DataComponentType<Integer> type = intType(key);
		if (type == null || !stack.has(type)) {
			return fallback;
		}
		Integer value = stack.get(type);
		return value == null ? fallback : value;
	}

	public static void putInt(ItemStack stack, String key, int value) {
		DataComponentType<Integer> type = intType(key);
		if (type != null) {
			stack.set(type, value);
		}
	}

	public static boolean getBoolean(ItemStack stack, String key, boolean fallback) {
		DataComponentType<Boolean> type = booleanType(key);
		if (type == null || !stack.has(type)) {
			return fallback;
		}
		Boolean value = stack.get(type);
		return value == null ? fallback : value;
	}

	public static void putBoolean(ItemStack stack, String key, boolean value) {
		DataComponentType<Boolean> type = booleanType(key);
		if (type != null) {
			stack.set(type, value);
		}
	}

	public static void remove(ItemStack stack, String key) {
		DataComponentType<?> type = type(key);
		if (type != null) {
			stack.remove(type);
		}
	}

	private static DataComponentType<?> type(String key) {
		if (FTBIColorConstants.ARMOR_COLOR_NBT.equals(key) || FTBIColorConstants.SPRAY_KIT_SELECTED_COLOR_NBT.equals(key)) {
			return intType(key);
		}
		return booleanType(key);
	}

	private static DataComponentType<Integer> intType(String key) {
		if (FTBIColorConstants.ARMOR_COLOR_NBT.equals(key)) {
			return FTBIColorComponents.ARMOR_COLOR.get();
		}
		if (FTBIColorConstants.SPRAY_KIT_SELECTED_COLOR_NBT.equals(key)) {
			return FTBIColorComponents.SELECTED_COLOR.get();
		}
		return null;
	}

	private static DataComponentType<Boolean> booleanType(String key) {
		if (FTBIColorConstants.SPRAY_KIT_CURIO_ACTIVE_NBT.equals(key)) {
			return FTBIColorComponents.CURIO_ACTIVE.get();
		}
		return null;
	}
}
