package com.ftbicolor.item;

import com.ftbicolor.compat.FTBIColorCurios;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

public final class FTBIColorKitSource {
	public static final int MAIN_HAND = 0;
	public static final int OFF_HAND = 1;
	public static final int CURIO = 2;

	private FTBIColorKitSource() {
	}

	public static int fromHand(InteractionHand hand) {
		return hand == InteractionHand.OFF_HAND ? OFF_HAND : MAIN_HAND;
	}

	public static int locate(Player player) {
		if (player.getMainHandItem().getItem() instanceof FTBIColorSprayPaintKitItem) {
			return MAIN_HAND;
		}

		if (player.getOffhandItem().getItem() instanceof FTBIColorSprayPaintKitItem) {
			return OFF_HAND;
		}

		if (curiosLoaded() && !FTBIColorCurios.findKit(player).isEmpty()) {
			return CURIO;
		}

		return -1;
	}

	public static ItemStack stack(Player player, int source) {
		if (source == OFF_HAND) {
			return player.getOffhandItem();
		}

		if (source == CURIO && curiosLoaded()) {
			return FTBIColorCurios.findKit(player);
		}

		return player.getMainHandItem();
	}

	public static void save(Player player, int source, ItemStack stack) {
		if (source == CURIO) {
			if (curiosLoaded()) {
				FTBIColorCurios.save(player, stack);
			}
			return;
		}

		player.setItemInHand(source == OFF_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND, stack);
	}

	private static boolean curiosLoaded() {
		return ModList.get().isLoaded("curios");
	}
}
