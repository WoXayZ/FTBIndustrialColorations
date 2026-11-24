package com.ftbicolor.client;

import com.ftbicolor.item.FTBIColorKitSource;
import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import com.ftbicolor.item.FTBIColorSprayPaintKitType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class FTBIColorSprayPaintWheelOpener {
	private FTBIColorSprayPaintWheelOpener() {
	}

	public static void open(Player player, int source, ItemStack stack, FTBIColorSprayPaintKitType kitType, boolean holdMode) {
		Minecraft.getInstance().setScreen(new FTBIColorSprayPaintWheelScreen(player, source, kitType, holdMode));
	}

	public static void tryOpenFromHand(Player player, InteractionHand hand, boolean holdMode) {
		ItemStack stack = player.getItemInHand(hand);
		if (!(stack.getItem() instanceof FTBIColorSprayPaintKitItem kit)) {
			return;
		}

		open(player, FTBIColorKitSource.fromHand(hand), stack, kit.kitType, holdMode);
	}
}
