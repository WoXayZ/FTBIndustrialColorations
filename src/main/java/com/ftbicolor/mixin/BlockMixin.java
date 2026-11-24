package com.ftbicolor.mixin;

import com.ftbicolor.FTBIColorPick;
import com.ftbicolor.block.FTBIColorBlockStates;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
	@Inject(method = "getCloneItemStack", at = @At("RETURN"), cancellable = true)
	private void ftbicolor$keepPickedColor(BlockGetter level, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
		if (FTBIColorPick.isCtrlPick()) {
			cir.setReturnValue(FTBIColorBlockStates.withPickedColor(cir.getReturnValue(), state));
		}
	}
}
