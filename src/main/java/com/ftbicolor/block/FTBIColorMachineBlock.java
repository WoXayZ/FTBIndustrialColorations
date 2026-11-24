package com.ftbicolor.block;

import dev.ftb.mods.ftbic.block.FTBICBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

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
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		return FTBIColorBlockStates.ctrlPickedStack(FTBIColorBlockStates.originalStack(originalBlock), state);
	}

	public static FTBIColorMachineBlock machineBlock() {
		return new FTBIColorMachineBlock(
				Properties.copy(FTBICBlocks.MACHINE_BLOCK.get()),
				FTBICBlocks.MACHINE_BLOCK
		);
	}

	public static FTBIColorMachineBlock advancedMachineBlock() {
		return new FTBIColorMachineBlock(
				Properties.copy(FTBICBlocks.ADVANCED_MACHINE_BLOCK.get()),
				FTBICBlocks.ADVANCED_MACHINE_BLOCK
		);
	}
}
