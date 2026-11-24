package com.ftbicolor.item;

import com.ftbicolor.platform.FTBIColorItemSettings;
import com.ftbicolor.platform.FTBIColorText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;

import java.util.function.Consumer;

public class FTBIColorSprayPaintCanItem extends Item {
	public final DyeColor color;

	public FTBIColorSprayPaintCanItem(DyeColor color, Properties properties) {
		super(properties);
		this.color = color;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		return FTBIColorSprayPaintActions.paintBlock(context.getItemInHand(), context, color);
	}

	@Override
	public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
		return FTBIColorArmorPainter.tryPaintInventoryArmor(stack, slot, action, player, color);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
		tooltip.accept(FTBIColorText.translatable("item.ftbic.spray_paint_can.tooltip").withStyle(ChatFormatting.GRAY));
	}
}
