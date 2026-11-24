package com.ftbicolor.block;

import dev.ftb.mods.ftbic.block.FTBICBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
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
		protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
			return FTBIColorBlockStates.ctrlPickedStack(FTBIColorBlockStates.originalStack(originalBlock), state, includeData);
		}
	}

	public static final class Glass extends HalfTransparentBlock {
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
		protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
			return FTBIColorBlockStates.ctrlPickedStack(FTBIColorBlockStates.originalStack(originalBlock), state, includeData);
		}
	}

	public static Stone stone(Identifier id) {
		return new Stone(
				FTBIColorBlocks.props(id, SoundType.STONE, 10F, 10_000_000_000F, false),
				FTBICBlocks.REINFORCED_STONE
		);
	}

	public static Glass glass(Identifier id) {
		return new Glass(
				FTBIColorBlocks.props(id, SoundType.GLASS, 10F, 10_000_000_000F, true),
				FTBICBlocks.REINFORCED_GLASS
		);
	}
}
