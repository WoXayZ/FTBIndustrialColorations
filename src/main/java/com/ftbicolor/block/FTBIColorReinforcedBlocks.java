package com.ftbicolor.block;

import com.ftbicolor.FTBIColor;
import dev.ftb.mods.ftbic.block.FTBICBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
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
			this.registerDefaultState(this.defaultBlockState().setValue(FTBIColor.COLOR, DyeColor.GRAY));
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
	}

	public static final class Glass extends GlassBlock {
		private final Supplier<? extends Block> originalBlock;

		public Glass(Properties properties, Supplier<? extends Block> originalBlock) {
			super(properties);
			this.originalBlock = originalBlock;
			this.registerDefaultState(this.defaultBlockState().setValue(FTBIColor.COLOR, DyeColor.GRAY));
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
