package com.ftbicolor.client;

import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import com.ftbicolor.item.FTBIColorSprayPaintKitType;
import com.ftbicolor.item.FTBIColorSprayPaintKitUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class FTBIColorSprayPaintWheelOpener {
	private FTBIColorSprayPaintWheelOpener() {
	}

	public static boolean hasControlDown() {
		return Screen.hasControlDown();
	}

	public static void open(Player player, InteractionHand hand, ItemStack stack, FTBIColorSprayPaintKitType kitType, boolean holdMode) {
		Minecraft.getInstance().setScreen(new FTBIColorSprayPaintWheelScreen(player, hand, kitType, holdMode));
	}

	public static void tryOpenFromHand(Player player, InteractionHand hand, boolean holdMode) {
		ItemStack stack = player.getItemInHand(hand);
		if (!(stack.getItem() instanceof FTBIColorSprayPaintKitItem kit)) {
			return;
		}

		if (!FTBIColorSprayPaintKitUtil.shouldOpenWheel(player, stack, hasControlDown())) {
			return;
		}

		open(player, hand, stack, kit.kitType, holdMode);
	}

	public static InteractionHand findKitHand(Player player) {
		for (InteractionHand hand : InteractionHand.values()) {
			if (player.getItemInHand(hand).getItem() instanceof FTBIColorSprayPaintKitItem) {
				return hand;
			}
		}

		return InteractionHand.MAIN_HAND;
	}
}
