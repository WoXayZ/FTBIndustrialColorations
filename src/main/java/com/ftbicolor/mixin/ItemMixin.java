package com.ftbicolor.mixin;

import com.ftbicolor.block.FTBIColorBlockStates;
import com.ftbicolor.compat.FTBIColorCurios;
import com.ftbicolor.item.FTBIColorArmorPainter;
import com.ftbicolor.platform.FTBIColorDye;
import com.ftbicolor.platform.FTBIColorText;
import dev.ftb.mods.ftbic.item.SprayPaintCanItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.function.Consumer;

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
			if (ModList.get().isLoaded("curios") && FTBIColorCurios.tryPaintInventoryArmor(slot, action, player)) {
				cir.setReturnValue(true);
			}
			return;
		}

		if (FTBIColorArmorPainter.tryPaintInventoryArmor(stack, slot, action, player, FTBIColorDye.fromVanillaCan(sprayCan.dark))) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "appendHoverText", at = @At("RETURN"))
	private void ftbicolor$pickedColorTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag, CallbackInfo ci) {
		DyeColor color = FTBIColorBlockStates.pickedColor(stack);
		if (color == null) {
			return;
		}
		Component name = FTBIColorText.translatable("color.minecraft." + color.getName()).withStyle(FTBIColorDye.chatFormatting(color));
		tooltip.accept(FTBIColorText.translatable("item.ftbicolor.picked_color", name).withStyle(ChatFormatting.DARK_GRAY));
	}
}
