package com.ftbicolor.block;

import com.ftbicolor.FTBIColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class FTBIColorBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, FTBIColor.MOD_ID);

	public static final RegistryObject<ColorableMachineBlock> COLORED_MACHINE_BLOCK =
			REGISTRY.register("colored_machine_block", ColorableMachineBlock::machineBlock);

	public static final RegistryObject<ColorableMachineBlock> COLORED_ADVANCED_MACHINE_BLOCK =
			REGISTRY.register("colored_advanced_machine_block", ColorableMachineBlock::advancedMachineBlock);

	private FTBIColorBlocks() {
	}
}
