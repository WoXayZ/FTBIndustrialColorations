package com.ftbicolor.block;

import com.ftbicolor.FTBIColorConstants;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class FTBIColorBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, FTBIColorConstants.MOD_ID);

	public static final RegistryObject<FTBIColorMachineBlock> COLORED_MACHINE_BLOCK =
			REGISTRY.register("colored_machine_block", FTBIColorMachineBlock::machineBlock);

	public static final RegistryObject<FTBIColorMachineBlock> COLORED_ADVANCED_MACHINE_BLOCK =
			REGISTRY.register("colored_advanced_machine_block", FTBIColorMachineBlock::advancedMachineBlock);

	public static final RegistryObject<FTBIColorReinforcedBlocks.Stone> COLORED_REINFORCED_STONE =
			REGISTRY.register("colored_reinforced_stone", FTBIColorReinforcedBlocks::stone);

	public static final RegistryObject<FTBIColorReinforcedBlocks.Glass> COLORED_REINFORCED_GLASS =
			REGISTRY.register("colored_reinforced_glass", FTBIColorReinforcedBlocks::glass);

	private FTBIColorBlocks() {
	}
}
