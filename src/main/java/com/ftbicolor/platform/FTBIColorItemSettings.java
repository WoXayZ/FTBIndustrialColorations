package com.ftbicolor.platform;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public final class FTBIColorItemSettings {
	private FTBIColorItemSettings() {
	}

	public static Item.Properties single(Identifier id) {
		return new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, id));
	}
}
