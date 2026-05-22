package com.ftbicolor.block;

import com.ftbicolor.FTBIColor;
import dev.ftb.mods.ftbic.block.FTBICBlocks;
import dev.ftb.mods.ftbic.block.SprayPaintable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class FTBIColorBlockPainter {
	private FTBIColorBlockPainter() {
	}

	public static boolean isPaintable(BlockState state) {
		Block block = state.getBlock();
		if (block instanceof SprayPaintable) {
			return true;
		}
		if (state.hasProperty(FTBIColor.COLOR)) {
			return true;
		}
		return block == FTBICBlocks.MACHINE_BLOCK.get()
				|| block == FTBICBlocks.ADVANCED_MACHINE_BLOCK.get()
				|| block == FTBICBlocks.REINFORCED_STONE.get()
				|| block == FTBICBlocks.REINFORCED_GLASS.get();
	}

	public static boolean paint(BlockState state, Level level, BlockPos pos, DyeColor color) {
		Block block = state.getBlock();

		if (block == FTBICBlocks.MACHINE_BLOCK.get()) {
			return swapTo(FTBIColorBlocks.COLORED_MACHINE_BLOCK.get(), level, pos, color);
		}

		if (block == FTBICBlocks.ADVANCED_MACHINE_BLOCK.get()) {
			return swapTo(FTBIColorBlocks.COLORED_ADVANCED_MACHINE_BLOCK.get(), level, pos, color);
		}

		if (block == FTBICBlocks.REINFORCED_STONE.get()) {
			return swapTo(FTBIColorBlocks.COLORED_REINFORCED_STONE.get(), level, pos, color);
		}

		if (block == FTBICBlocks.REINFORCED_GLASS.get()) {
			return swapTo(FTBIColorBlocks.COLORED_REINFORCED_GLASS.get(), level, pos, color);
		}

		return paintInPlace(state, level, pos, color);
	}

	private static boolean paintInPlace(BlockState state, Level level, BlockPos pos, DyeColor color) {
		boolean dark = color == DyeColor.BLACK;
		boolean changed = false;
		BlockState target = state;

		if (target.hasProperty(SprayPaintable.DARK) && target.getValue(SprayPaintable.DARK) != dark) {
			target = target.setValue(SprayPaintable.DARK, dark);
			changed = true;
		}

		if (target.hasProperty(FTBIColor.COLOR) && target.getValue(FTBIColor.COLOR) != color) {
			target = target.setValue(FTBIColor.COLOR, color);
			changed = true;
		}

		if (changed) {
			level.setBlock(pos, target, 3);
		}

		return changed;
	}

	private static boolean swapTo(Block colored, Level level, BlockPos pos, DyeColor color) {
		BlockState current = level.getBlockState(pos);
		BlockState target = colored.defaultBlockState().setValue(FTBIColor.COLOR, color);

		if (current.getBlock() == colored && current.getValue(FTBIColor.COLOR) == color) {
			return false;
		}

		level.setBlock(pos, target, 3);
		return true;
	}
}
