package com.ftbicolor.block;

import dev.ftb.mods.ftbic.block.FTBICBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.function.Supplier;

public final class FTBIColorReinforcedBlocks {
	private FTBIColorReinforcedBlocks() {
	}

	public static final class Stone extends Block {
		private final Supplier<? extends Block> originalBlock;

		public Stone(Properties properties, Supplier<? extends Block> originalBlock) {
			super(properties);
			this.originalBlock = originalBlock;
			this.registerDefaultState(FTBIColorBlockStates.withColor(this.defaultBlockState(), DyeColor.GRAY));
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
	}

	public static final class Glass extends GlassBlock {
		private final Supplier<? extends Block> originalBlock;

		public Glass(Properties properties, Supplier<? extends Block> originalBlock) {
			super(properties);
			this.originalBlock = originalBlock;
			this.registerDefaultState(FTBIColorBlockStates.withColor(this.defaultBlockState(), DyeColor.GRAY));
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
	}

	public static Stone stone() {
		return new Stone(
				Block.Properties.copy(FTBICBlocks.REINFORCED_STONE.get()),
				FTBICBlocks.REINFORCED_STONE
		);
	}

	public static Glass glass() {
		return new Glass(
				Block.Properties.copy(FTBICBlocks.REINFORCED_GLASS.get()),
				FTBICBlocks.REINFORCED_GLASS
		);
	}
}
