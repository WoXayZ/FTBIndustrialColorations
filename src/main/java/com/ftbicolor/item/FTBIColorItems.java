package com.ftbicolor.item;

import com.ftbicolor.FTBIColorConstants;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public final class FTBIColorItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, FTBIColorConstants.MOD_ID);

	public static final Map<net.minecraft.world.item.DyeColor, RegistryObject<Item>> SPRAY_PAINT_CANS = new EnumMap<>(net.minecraft.world.item.DyeColor.class);

	public static final RegistryObject<FTBIColorSprayPaintKitItem> COLORED_SPRAY_PAINT_KIT_PRIMARY =
			REGISTRY.register(FTBIColorSprayPaintKitType.PRIMARY.registryName(), () -> new FTBIColorSprayPaintKitItem(FTBIColorSprayPaintKitType.PRIMARY));

	public static final RegistryObject<FTBIColorSprayPaintKitItem> COLORED_SPRAY_PAINT_KIT_SECONDARY =
			REGISTRY.register(FTBIColorSprayPaintKitType.SECONDARY.registryName(), () -> new FTBIColorSprayPaintKitItem(FTBIColorSprayPaintKitType.SECONDARY));

	public static final RegistryObject<FTBIColorSprayPaintKitItem> COLORED_SPRAY_PAINT_SELECTOR =
			REGISTRY.register(FTBIColorSprayPaintKitType.FULL.registryName(), () -> new FTBIColorSprayPaintKitItem(FTBIColorSprayPaintKitType.FULL));

	static {
		for (net.minecraft.world.item.DyeColor color : net.minecraft.world.item.DyeColor.values()) {
			if (color == net.minecraft.world.item.DyeColor.WHITE || color == net.minecraft.world.item.DyeColor.BLACK) {
				continue;
			}

			String id = color.getName().toLowerCase(Locale.ROOT) + "_spray_paint_can";
			SPRAY_PAINT_CANS.put(color, REGISTRY.register(id, () -> new FTBIColorSprayPaintCanItem(color)));
		}
	}

	private FTBIColorItems() {
	}
}
