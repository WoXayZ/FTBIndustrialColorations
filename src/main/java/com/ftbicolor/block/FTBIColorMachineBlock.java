package com.ftbicolor.block;

import com.ftbicolor.FTBIColor;
import dev.ftb.mods.ftbic.block.FTBICBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
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
		this.registerDefaultState(this.defaultBlockState().setValue(FTBIColor.COLOR, DyeColor.WHITE));
	}

	public Block getOriginalBlock() {
		return originalBlock.get();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FTBIColor.COLOR);
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		Item item = originalBlock.get().asItem();
		return item == null ? ItemStack.EMPTY : new ItemStack(item);
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
