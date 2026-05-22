package com.ftbicolor.mixin;

import com.ftbicolor.block.ColorPainter;
import dev.ftb.mods.ftbic.item.SprayPaintCanItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SprayPaintCanItem.class)
public abstract class SprayPaintCanItemMixin {
	@Shadow(remap = false) @Final public boolean dark;

	@Inject(
			method = "onItemUseFirst",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	private void ftbicolor$paintWithColor(ItemStack stack, UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		BlockState state = context.getLevel().getBlockState(context.getClickedPos());

		if (!ColorPainter.isPaintable(state)) {
			return;
		}

		DyeColor target = dark ? DyeColor.BLACK : DyeColor.WHITE;
		boolean painted = ColorPainter.paint(state, context.getLevel(), context.getClickedPos(), target);

		if (painted && context.getLevel().isClientSide()) {
			float pitch = 2.6F + (context.getLevel().random.nextFloat() - context.getLevel().random.nextFloat()) * 0.8F;
			context.getLevel().playSound(
					context.getPlayer(),
					context.getClickedPos(),
					SoundEvents.REDSTONE_TORCH_BURNOUT,
					SoundSource.BLOCKS,
					0.5F,
					pitch
			);
		}

		cir.setReturnValue(InteractionResult.SUCCESS);
	}
}
