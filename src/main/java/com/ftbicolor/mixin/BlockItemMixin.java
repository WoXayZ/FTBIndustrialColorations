package com.ftbicolor.mixin;

import com.ftbicolor.block.FTBIColorBlockPainter;
import com.ftbicolor.block.FTBIColorBlockStates;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
	@Inject(method = "place", at = @At("RETURN"))
	private void ftbicolor$placePickedColor(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
		if (!cir.getReturnValue().consumesAction()) {
			return;
		}
		DyeColor color = FTBIColorBlockStates.pickedColor(context.getItemInHand());
		if (color == null) {
			return;
		}
		Level level = context.getLevel();
		BlockState state = level.getBlockState(context.getClickedPos());
		FTBIColorBlockPainter.paint(state, level, context.getClickedPos(), color);
	}
}
