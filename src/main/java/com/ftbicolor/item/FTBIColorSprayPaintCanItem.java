package com.ftbicolor.item;

import dev.ftb.mods.ftbic.FTBIC;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FTBIColorSprayPaintCanItem extends Item {
	public final DyeColor color;

	public FTBIColorSprayPaintCanItem(DyeColor color) {
		super(new Properties().stacksTo(1).tab(FTBIC.TAB));
		this.color = color;
	}

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		return FTBIColorSprayPaintActions.paintBlock(stack, context, color);
	}

	@Override
	public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
		return FTBIColorArmorPainter.tryPaintInventoryArmor(stack, slot, action, player, color);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(new TranslatableComponent("item.ftbic.spray_paint_can.tooltip").withStyle(ChatFormatting.GRAY));
	}
}
