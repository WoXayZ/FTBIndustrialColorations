package com.ftbicolor.item;

import com.ftbicolor.FTBIColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public final class FTBIColorItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, FTBIColor.MOD_ID);

	public static final Map<DyeColor, RegistryObject<Item>> SPRAY_PAINT_CANS = new EnumMap<>(DyeColor.class);

	static {
		for (DyeColor color : DyeColor.values()) {
			if (color == DyeColor.WHITE || color == DyeColor.BLACK) {
				continue;
			}

			String id = color.getName().toLowerCase(Locale.ROOT) + "_spray_paint_can";
			SPRAY_PAINT_CANS.put(color, REGISTRY.register(id, () -> new ColoredSprayPaintCanItem(color)));
		}
	}

	private FTBIColorItems() {
	}
}
