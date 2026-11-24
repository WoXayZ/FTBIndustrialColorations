package com.ftbicolor.mixin;

import com.ftbicolor.block.FTBIColorBlockStates;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockMixin {
	@Inject(
			method = "getCloneItemStack(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Lnet/minecraft/world/item/ItemStack;",
			at = @At("RETURN"),
			cancellable = true
	)
	private void ftbicolor$keepPickedColor(LevelReader level, BlockPos pos, BlockState state, boolean includeData, CallbackInfoReturnable<ItemStack> cir) {
		if (includeData) {
			cir.setReturnValue(FTBIColorBlockStates.withPickedColor(cir.getReturnValue(), state));
		}
	}
}
