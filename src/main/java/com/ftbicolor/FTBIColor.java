package com.ftbicolor;

import com.ftbicolor.block.FTBIColorBlocks;
import com.ftbicolor.item.FTBIColorItems;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FTBIColor.MOD_ID)
public class FTBIColor {
	public static final String MOD_ID = "ftbicolor";
	public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);

	public FTBIColor() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		FTBIColorBlocks.REGISTRY.register(modBus);
		FTBIColorItems.REGISTRY.register(modBus);
	}
}
