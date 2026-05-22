package com.ftbicolor.item;

import com.ftbicolor.client.FTBIColorSprayPaintKitTooltips;
import dev.ftb.mods.ftbic.FTBIC;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FTBIColorSprayPaintKitItem extends Item {
	public final FTBIColorSprayPaintKitType kitType;

	public FTBIColorSprayPaintKitItem(FTBIColorSprayPaintKitType kitType) {
		super(new Properties().stacksTo(1).tab(FTBIC.TAB));
		this.kitType = kitType;
	}

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		if (FTBIColorSprayPaintKitColor.getSelected(stack) == null) {
			return InteractionResult.PASS;
		}

		DyeColor color = FTBIColorSprayPaintKitColor.getSelected(stack);
		return FTBIColorSprayPaintActions.paintBlock(stack, context, color);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return InteractionResultHolder.pass(player.getItemInHand(hand));
	}

	@Override
	public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
		DyeColor color = FTBIColorSprayPaintKitColor.getSelected(stack);
		if (color == null) {
			return false;
		}

		return FTBIColorSprayPaintActions.paintInventoryArmor(stack, slot, action, player, color);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(new TranslatableComponent("item.ftbic.spray_paint_can.tooltip").withStyle(ChatFormatting.GRAY));
		list.add(new TranslatableComponent(
				"item.ftbicolor.spray_paint_kit.selected",
				FTBIColorSprayPaintKitColor.selectedColorName(stack)
		).withStyle(ChatFormatting.DARK_GRAY));
		Component wheelHint = DistExecutor.safeCallWhenOn(
				Dist.CLIENT,
				() -> FTBIColorSprayPaintKitTooltips::openWheelHint
		);
		if (wheelHint != null) {
			list.add(wheelHint.copy().withStyle(ChatFormatting.DARK_GRAY));
		}
	}
}
