package com.ftbicolor.platform;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public final class FTBIColorStackData {
	private FTBIColorStackData() {
	}

	public static boolean has(ItemStack stack, String key) {
		if (!stack.hasTag()) {
			return false;
		}

		CompoundTag tag = stack.getTag();
		return tag != null && tag.contains(key);
	}

	public static int getInt(ItemStack stack, String key, int fallback) {
		if (!has(stack, key)) {
			return fallback;
		}

		CompoundTag tag = stack.getTag();
		return tag == null ? fallback : tag.getInt(key);
	}

	public static void putInt(ItemStack stack, String key, int value) {
		stack.getOrCreateTag().putInt(key, value);
	}

	public static boolean getBoolean(ItemStack stack, String key, boolean fallback) {
		if (!has(stack, key)) {
			return fallback;
		}

		CompoundTag tag = stack.getTag();
		return tag == null ? fallback : tag.getBoolean(key);
	}

	public static void putBoolean(ItemStack stack, String key, boolean value) {
		stack.getOrCreateTag().putBoolean(key, value);
	}

	public static void remove(ItemStack stack, String key) {
		if (!stack.hasTag()) {
			return;
		}

		CompoundTag tag = stack.getTag();
		if (tag == null) {
			return;
		}

		tag.remove(key);
		if (tag.isEmpty()) {
			stack.setTag(null);
		}
	}
}
