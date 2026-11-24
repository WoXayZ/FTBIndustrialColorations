package com.ftbicolor.client;

import com.ftbicolor.item.FTBIColorArmorUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public final class FTBIColorArmorAssets {
	private FTBIColorArmorAssets() {
	}

	public static ResourceKey<EquipmentAsset> keyFor(ItemStack stack) {
		if (stack.isEmpty()) {
			return null;
		}

		Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
		if (id == null || !"ftbic".equals(id.getNamespace())) {
			return null;
		}

		String path = id.getPath();
		if (isPiece(path, "carbon")) {
			return asset("carbon");
		}

		if (!isPiece(path, "quantum")) {
			return null;
		}

		if (FTBIColorArmorUtil.hasExplicitColor(stack)) {
			DyeColor color = FTBIColorArmorUtil.getColor(stack);
			if (color != DyeColor.WHITE) {
				return asset("quantum_" + color.getSerializedName());
			}
		}

		return asset("quantum");
	}

	private static boolean isPiece(String path, String material) {
		return path.equals(material + "_helmet")
				|| path.equals(material + "_chestplate")
				|| path.equals(material + "_leggings")
				|| path.equals(material + "_boots");
	}

	@SuppressWarnings("unchecked")
	private static ResourceKey<EquipmentAsset> asset(String path) {
		return ResourceKey.create(
				(ResourceKey<? extends Registry<EquipmentAsset>>) EquipmentAssets.ROOT_ID,
				Identifier.fromNamespaceAndPath("ftbic", path)
		);
	}
}
