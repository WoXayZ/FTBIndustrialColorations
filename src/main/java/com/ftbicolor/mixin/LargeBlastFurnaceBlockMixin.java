package com.ftbicolor.mixin;

import com.ftbicolor.FTBIColor;
import com.ftbicolor.block.FTBIColorBlockStates;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.jjyp.ftbicec.block.LargeBlastFurnaceBlock")
public abstract class LargeBlastFurnaceBlockMixin extends Block {
	private LargeBlastFurnaceBlockMixin() {
		super(Properties.of());
	}

	@Inject(method = "createBlockStateDefinition", at = @At("HEAD"))
	private void ftbicolor$addColorProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
		FTBIColorBlockStates.defineColor(builder);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void ftbicolor$setDefaultColor(BlockBehaviour.Properties properties, CallbackInfo ci) {
		registerDefaultState(defaultBlockState().setValue(FTBIColor.COLOR, DyeColor.GRAY));
	}
}
