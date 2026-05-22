package com.ftbicolor.mixin;

import com.ftbicolor.item.FTBIColorArmorPainter;
import dev.ftb.mods.ftbic.item.SprayPaintCanItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
	@Inject(method = "overrideStackedOnOther", at = @At("HEAD"), cancellable = true)
	private void ftbicolor$sprayPaintInventoryArmor(
			ItemStack stack,
			Slot slot,
			ClickAction action,
			Player player,
			CallbackInfoReturnable<Boolean> cir
	) {
		Item item = stack.getItem();
		if (!(item instanceof SprayPaintCanItem sprayCan)) {
			return;
		}

		DyeColor target = sprayCan.dark ? DyeColor.BLACK : DyeColor.WHITE;
		if (FTBIColorArmorPainter.tryPaintInventoryArmor(stack, slot, action, player, target)) {
			cir.setReturnValue(true);
		}
	}
}
