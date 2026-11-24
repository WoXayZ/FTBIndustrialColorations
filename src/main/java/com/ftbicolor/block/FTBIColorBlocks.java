package com.ftbicolor.block;

import com.ftbicolor.FTBIColorConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class FTBIColorBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(FTBIColorConstants.MOD_ID);

	public static final DeferredBlock<FTBIColorMachineBlock> COLORED_MACHINE_BLOCK =
			REGISTRY.register("colored_machine_block", FTBIColorMachineBlock::machineBlock);

	public static final DeferredBlock<FTBIColorMachineBlock> COLORED_ADVANCED_MACHINE_BLOCK =
			REGISTRY.register("colored_advanced_machine_block", FTBIColorMachineBlock::advancedMachineBlock);

	public static final DeferredBlock<FTBIColorReinforcedBlocks.Stone> COLORED_REINFORCED_STONE =
			REGISTRY.register("colored_reinforced_stone", FTBIColorReinforcedBlocks::stone);

	public static final DeferredBlock<FTBIColorReinforcedBlocks.Glass> COLORED_REINFORCED_GLASS =
			REGISTRY.register("colored_reinforced_glass", FTBIColorReinforcedBlocks::glass);

	static BlockBehaviour.Properties props(Identifier id, SoundType sound, float destroyTime, float explosionResistance, boolean glass) {
		BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
				.setId(ResourceKey.create(Registries.BLOCK, id))
				.sound(sound)
				.strength(destroyTime, explosionResistance)
				.requiresCorrectToolForDrops();
		return glass ? properties.noOcclusion() : properties;
	}

	private FTBIColorBlocks() {
	}
}
