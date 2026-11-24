package com.ftbicolor.mixin;

import com.ftbicolor.item.FTBIColorSprayPaintActions;
import dev.ftb.mods.ftbic.item.SprayPaintCanItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SprayPaintCanItem.class)
public abstract class SprayPaintCanItemMixin {
	@Shadow(remap = false) @Final public boolean dark;

	@Inject(method = "onItemUseFirst", at = @At("HEAD"), cancellable = true, remap = false)
	private void ftbicolor$paintWithColor(ItemStack stack, UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		if (FTBIColorSprayPaintActions.consumeVanillaCan(context, dark)) {
			cir.setReturnValue(InteractionResult.SUCCESS);
		}
	}
}
