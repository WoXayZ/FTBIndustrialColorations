package com.ftbicolor;

import com.ftbicolor.block.FTBIColorBlocks;
import com.ftbicolor.compat.FTBIColorCurios;
import com.ftbicolor.item.FTBIColorItems;
import com.ftbicolor.network.FTBIColorNetwork;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FTBIColorConstants.MOD_ID)
public class FTBIColor {
	public static final String MOD_ID = FTBIColorConstants.MOD_ID;
	public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
	public static final BooleanProperty PAINTED = BooleanProperty.create("painted");

	public FTBIColor() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		FTBIColorBlocks.REGISTRY.register(modBus);
		FTBIColorItems.REGISTRY.register(modBus);
		FTBIColorNetwork.register();
		if (ModList.get().isLoaded("curios")) {
			FTBIColorCurios.init(modBus);
		}
	}
}
