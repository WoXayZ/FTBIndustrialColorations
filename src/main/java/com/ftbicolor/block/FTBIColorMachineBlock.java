package com.ftbicolor.block;

import dev.ftb.mods.ftbic.block.FTBICBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.LevelReader;

import java.util.function.Supplier;

public class FTBIColorMachineBlock extends Block {
	private final Supplier<? extends Block> originalBlock;

	public FTBIColorMachineBlock(Properties properties, Supplier<? extends Block> originalBlock) {
		super(properties);
		this.originalBlock = originalBlock;
		this.registerDefaultState(FTBIColorBlockStates.withColor(this.defaultBlockState(), DyeColor.WHITE));
	}

	public Block getOriginalBlock() {
		return originalBlock.get();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		FTBIColorBlockStates.defineColor(builder);
	}

	@Override
	protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
		return FTBIColorBlockStates.ctrlPickedStack(FTBIColorBlockStates.originalStack(originalBlock), state, includeData);
	}

	public static FTBIColorMachineBlock machineBlock(Identifier id) {
		return new FTBIColorMachineBlock(
				FTBIColorBlocks.props(id, SoundType.METAL, 5F, 6F, false),
				FTBICBlocks.MACHINE_BLOCK
		);
	}

	public static FTBIColorMachineBlock advancedMachineBlock(Identifier id) {
		return new FTBIColorMachineBlock(
				FTBIColorBlocks.props(id, SoundType.METAL, 5F, 6F, false),
				FTBICBlocks.ADVANCED_MACHINE_BLOCK
		);
	}
}
