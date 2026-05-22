package com.ftbicolor.item;

import com.ftbicolor.FTBIColorConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public final class FTBIColorArmorUtil {
	private FTBIColorArmorUtil() {
	}

	public static boolean isQuantumArmor(ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}

		ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
		if (id == null) {
			return false;
		}

		for (ResourceLocation quantumId : FTBIColorConstants.QUANTUM_ARMOR_ITEMS) {
			if (id.equals(quantumId)) {
				return true;
			}
		}

		return false;
	}

	public static DyeColor getColor(ItemStack stack) {
		if (!stack.hasTag()) {
			return DyeColor.WHITE;
		}

		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains(FTBIColorConstants.ARMOR_COLOR_NBT)) {
			return DyeColor.byId(tag.getInt(FTBIColorConstants.ARMOR_COLOR_NBT));
		}

		return DyeColor.WHITE;
	}

	public static boolean setColor(ItemStack stack, DyeColor color) {
		if (!isQuantumArmor(stack)) {
			return false;
		}

		if (getColor(stack) == color) {
			return false;
		}

		stack.getOrCreateTag().putInt(FTBIColorConstants.ARMOR_COLOR_NBT, color.getId());
		return true;
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
