package com.ftbicolor.item;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.platform.FTBIColorItemSettings;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public final class FTBIColorItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(FTBIColorConstants.MOD_ID);

	public static final Map<net.minecraft.world.item.DyeColor, DeferredItem<Item>> SPRAY_PAINT_CANS = new EnumMap<>(net.minecraft.world.item.DyeColor.class);

	public static final DeferredItem<FTBIColorSprayPaintKitItem> COLORED_SPRAY_PAINT_KIT_PRIMARY =
			REGISTRY.register(FTBIColorSprayPaintKitType.PRIMARY.registryName(), id -> new FTBIColorSprayPaintKitItem(FTBIColorSprayPaintKitType.PRIMARY, FTBIColorItemSettings.single(id)));

	public static final DeferredItem<FTBIColorSprayPaintKitItem> COLORED_SPRAY_PAINT_KIT_SECONDARY =
			REGISTRY.register(FTBIColorSprayPaintKitType.SECONDARY.registryName(), id -> new FTBIColorSprayPaintKitItem(FTBIColorSprayPaintKitType.SECONDARY, FTBIColorItemSettings.single(id)));

	public static final DeferredItem<FTBIColorSprayPaintKitItem> COLORED_SPRAY_PAINT_SELECTOR =
			REGISTRY.register(FTBIColorSprayPaintKitType.FULL.registryName(), id -> new FTBIColorSprayPaintKitItem(FTBIColorSprayPaintKitType.FULL, FTBIColorItemSettings.single(id)));

	public static final DeferredItem<BlockItem> COLORED_MACHINE_BLOCK = blockItem("colored_machine_block", com.ftbicolor.block.FTBIColorBlocks.COLORED_MACHINE_BLOCK);
	public static final DeferredItem<BlockItem> COLORED_ADVANCED_MACHINE_BLOCK = blockItem("colored_advanced_machine_block", com.ftbicolor.block.FTBIColorBlocks.COLORED_ADVANCED_MACHINE_BLOCK);
	public static final DeferredItem<BlockItem> COLORED_REINFORCED_STONE = blockItem("colored_reinforced_stone", com.ftbicolor.block.FTBIColorBlocks.COLORED_REINFORCED_STONE);
	public static final DeferredItem<BlockItem> COLORED_REINFORCED_GLASS = blockItem("colored_reinforced_glass", com.ftbicolor.block.FTBIColorBlocks.COLORED_REINFORCED_GLASS);

	static {
		for (net.minecraft.world.item.DyeColor color : net.minecraft.world.item.DyeColor.values()) {
			if (color == net.minecraft.world.item.DyeColor.WHITE || color == net.minecraft.world.item.DyeColor.BLACK) {
				continue;
			}

			String id = color.getName().toLowerCase(Locale.ROOT) + "_spray_paint_can";
			SPRAY_PAINT_CANS.put(color, REGISTRY.register(id, name -> new FTBIColorSprayPaintCanItem(color, FTBIColorItemSettings.single(name))));
		}
	}

	private static DeferredItem<BlockItem> blockItem(String name, Supplier<? extends Block> block) {
		return REGISTRY.register(name, (Identifier id) -> new BlockItem(block.get(), FTBIColorItemSettings.single(id).stacksTo(64)));
	}

	private FTBIColorItems() {
	}
}
