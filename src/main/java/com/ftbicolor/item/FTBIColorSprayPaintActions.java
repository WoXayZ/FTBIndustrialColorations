package com.ftbicolor.item;

import com.ftbicolor.advancement.FTBIColorAdvancements;
import com.ftbicolor.block.FTBIColorBlockPainter;
import com.ftbicolor.platform.FTBIColorDye;
import com.ftbicolor.platform.FTBIColorSounds;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

public final class FTBIColorSprayPaintActions {
	private FTBIColorSprayPaintActions() {
	}

	public static InteractionResult paintBlock(ItemStack stack, UseOnContext context, DyeColor color) {
		if (!applyBlockColor(context, color)) {
			return InteractionResult.PASS;
		}

		return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
	}

	public static boolean consumeVanillaCan(UseOnContext context, boolean dark) {
		return applyBlockColor(context, FTBIColorDye.fromVanillaCan(dark));
	}

	private static boolean applyBlockColor(UseOnContext context, DyeColor color) {
		BlockState state = context.getLevel().getBlockState(context.getClickedPos());
		if (!FTBIColorBlockPainter.isPaintable(state)) {
			return false;
		}

		boolean painted = FTBIColorBlockPainter.paint(state, context.getLevel(), context.getClickedPos(), color);
		if (painted) {
			if (context.getLevel().isClientSide()) {
				FTBIColorSounds.playOnBlock(context.getLevel(), context.getPlayer(), context.getClickedPos());
			} else if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
				FTBIColorAdvancements.markSprayPaintUsed(serverPlayer, color);
			}
		}

		return true;
	}

	public static boolean paintInventoryArmor(
			ItemStack stack,
			Slot slot,
			ClickAction action,
			Player player,
			DyeColor color
	) {
		return FTBIColorArmorPainter.tryPaintInventoryArmor(stack, slot, action, player, color);
	}
}
