package com.ftbicolor.platform;

import dev.ftb.mods.ftbic.FTBIC;
import net.minecraft.world.item.Item;

public final class FTBIColorItemSettings {
	private FTBIColorItemSettings() {
	}

	public static Item.Properties singleStack() {
		return new Item.Properties().stacksTo(1).tab(FTBIC.TAB);
	}
}
