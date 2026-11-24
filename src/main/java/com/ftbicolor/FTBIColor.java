package com.ftbicolor;

import com.ftbicolor.block.FTBIColorBlocks;
import com.ftbicolor.compat.FTBIColorCurios;
import com.ftbicolor.item.FTBIColorItems;
import com.ftbicolor.platform.FTBIColorAttachments;
import com.ftbicolor.platform.FTBIColorComponents;
import dev.ftb.mods.ftbic.registry.ModCreativeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(FTBIColorConstants.MOD_ID)
public class FTBIColor {
	public static final String MOD_ID = FTBIColorConstants.MOD_ID;
	public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
	public static final BooleanProperty PAINTED = BooleanProperty.create("painted");

	public FTBIColor(IEventBus modBus) {
		FTBIColorBlocks.REGISTRY.register(modBus);
		FTBIColorItems.REGISTRY.register(modBus);
		FTBIColorComponents.REGISTRY.register(modBus);
		FTBIColorAttachments.REGISTRY.register(modBus);
		modBus.addListener(FTBIColor::addCreative);
		if (ModList.get().isLoaded("curios")) {
			FTBIColorCurios.init(modBus);
		}
	}

	private static void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() != ModCreativeTabs.FTBIC_TAB.getKey()) {
			return;
		}
		FTBIColorItems.SPRAY_PAINT_CANS.values().forEach(item -> event.accept(item.get()));
		event.accept(FTBIColorItems.COLORED_SPRAY_PAINT_KIT_PRIMARY.get());
		event.accept(FTBIColorItems.COLORED_SPRAY_PAINT_KIT_SECONDARY.get());
		event.accept(FTBIColorItems.COLORED_SPRAY_PAINT_SELECTOR.get());
	}
}
