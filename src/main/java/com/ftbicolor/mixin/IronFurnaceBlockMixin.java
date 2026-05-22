package com.ftbicolor.mixin;

import com.ftbicolor.FTBIColor;
import dev.ftb.mods.ftbic.block.IronFurnaceBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronFurnaceBlock.class)
public abstract class IronFurnaceBlockMixin extends FurnaceBlock {
	private IronFurnaceBlockMixin() {
		super(Properties.of(net.minecraft.world.level.material.Material.METAL));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FTBIColor.COLOR);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void ftbicolor$setDefaultColor(CallbackInfo ci) {
		this.registerDefaultState(this.defaultBlockState().setValue(FTBIColor.COLOR, DyeColor.WHITE));
	}
}
