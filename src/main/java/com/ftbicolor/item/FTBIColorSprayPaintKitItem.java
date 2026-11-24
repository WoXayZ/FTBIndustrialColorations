package com.ftbicolor.item;

import com.ftbicolor.platform.FTBIColorClientHooks;
import com.ftbicolor.platform.FTBIColorItemSettings;
import com.ftbicolor.platform.FTBIColorText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FTBIColorSprayPaintKitItem extends Item {
	public final FTBIColorSprayPaintKitType kitType;

	public FTBIColorSprayPaintKitItem(FTBIColorSprayPaintKitType kitType) {
		super(FTBIColorItemSettings.singleStack());
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
		list.add(FTBIColorText.translatable("item.ftbic.spray_paint_can.tooltip").withStyle(ChatFormatting.GRAY));
		list.add(FTBIColorText.translatable(
				"item.ftbicolor.spray_paint_kit.selected",
				FTBIColorSprayPaintKitColor.selectedColorName(stack)
		).withStyle(ChatFormatting.DARK_GRAY));
		if (ModList.get().isLoaded("curios")) {
			boolean active = FTBIColorSprayPaintKitColor.isCurioActive(stack);
			list.add(FTBIColorText.translatable(active
					? "item.ftbicolor.spray_paint_kit.curio_active"
					: "item.ftbicolor.spray_paint_kit.curio_inactive"
			).withStyle(active ? ChatFormatting.GREEN : ChatFormatting.RED));
			list.add(FTBIColorText.translatable("item.ftbicolor.spray_paint_kit.curio_toggle").withStyle(ChatFormatting.DARK_GRAY));
		}
		Component wheelHint = FTBIColorClientHooks.wheelHint();
		if (wheelHint != null) {
			list.add(wheelHint.copy().withStyle(ChatFormatting.DARK_GRAY));
		}
	}
}
