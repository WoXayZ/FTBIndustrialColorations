package com.ftbicolor.block;

import com.ftbicolor.FTBIColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;

public class FTBIColorFirebricksBlock extends Block {
	public FTBIColorFirebricksBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(FTBIColor.COLOR, DyeColor.GRAY));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FTBIColor.COLOR);
	}
}
