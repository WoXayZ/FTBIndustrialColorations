package com.ftbicolor.item;

import com.ftbicolor.platform.FTBIColorClientHooks;
import com.ftbicolor.platform.FTBIColorItemSettings;
import com.ftbicolor.platform.FTBIColorText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;

import java.util.function.Consumer;

public class FTBIColorSprayPaintKitItem extends Item {
	public final FTBIColorSprayPaintKitType kitType;

	public FTBIColorSprayPaintKitItem(FTBIColorSprayPaintKitType kitType, Properties properties) {
		super(properties);
		this.kitType = kitType;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		ItemStack stack = context.getItemInHand();
		if (FTBIColorSprayPaintKitColor.getSelected(stack) == null) {
			return InteractionResult.PASS;
		}

		DyeColor color = FTBIColorSprayPaintKitColor.getSelected(stack);
		return FTBIColorSprayPaintActions.paintBlock(stack, context, color);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		return InteractionResult.PASS;
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
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
		tooltip.accept(FTBIColorText.translatable("item.ftbic.spray_paint_can.tooltip").withStyle(ChatFormatting.GRAY));
		tooltip.accept(FTBIColorText.translatable(
				"item.ftbicolor.spray_paint_kit.selected",
				FTBIColorSprayPaintKitColor.selectedColorName(stack)
		).withStyle(ChatFormatting.DARK_GRAY));
		if (ModList.get().isLoaded("curios")) {
			boolean active = FTBIColorSprayPaintKitColor.isCurioActive(stack);
			tooltip.accept(FTBIColorText.translatable(active
					? "item.ftbicolor.spray_paint_kit.curio_active"
					: "item.ftbicolor.spray_paint_kit.curio_inactive"
			).withStyle(active ? ChatFormatting.GREEN : ChatFormatting.RED));
			tooltip.accept(FTBIColorText.translatable("item.ftbicolor.spray_paint_kit.curio_toggle").withStyle(ChatFormatting.DARK_GRAY));
		}
		Component wheelHint = FTBIColorClientHooks.wheelHint();
		if (wheelHint != null) {
			tooltip.accept(wheelHint.copy().withStyle(ChatFormatting.DARK_GRAY));
		}
	}
}
