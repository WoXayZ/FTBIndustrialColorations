package com.ftbicolor.item;

import com.ftbicolor.advancement.FTBIColorAdvancements;
import com.ftbicolor.block.FTBIColorBlockPainter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class FTBIColorSprayPaintActions {
	private FTBIColorSprayPaintActions() {
	}

	public static InteractionResult paintBlock(ItemStack stack, UseOnContext context, DyeColor color) {
		BlockState state = context.getLevel().getBlockState(context.getClickedPos());

		if (!FTBIColorBlockPainter.isPaintable(state)) {
			return InteractionResult.PASS;
		}

		boolean painted = FTBIColorBlockPainter.paint(state, context.getLevel(), context.getClickedPos(), color);

		if (painted) {
			if (context.getLevel().isClientSide()) {
				playPaintSound(context.getLevel(), context.getPlayer(), context.getClickedPos());
			} else if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
				FTBIColorAdvancements.markSprayPaintUsed(serverPlayer, color);
			}
		}

		return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
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

	public static void playPaintSound(Level level, Player player, net.minecraft.core.BlockPos pos) {
		float pitch = 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F;
		level.playSound(
				player,
				pos,
				SoundEvents.REDSTONE_TORCH_BURNOUT,
				SoundSource.BLOCKS,
				0.5F,
				pitch
		);
	}
}
