package com.ftbicolor.client;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import com.ftbicolor.item.FTBIColorSprayPaintKitUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FTBIColorConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class FTBIColorSprayPaintWheelHandler {
	private static boolean wheelKeyWasDown;
	private static boolean holdWheelSessionActive;

	private FTBIColorSprayPaintWheelHandler() {
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) {
			return;
		}

		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.player == null) {
			return;
		}

		boolean keyDown = FTBIColorKeyBindings.SPRAY_PAINT_WHEEL.isDown();

		if (!keyDown) {
			holdWheelSessionActive = false;
		}

		if (minecraft.screen instanceof FTBIColorSprayPaintWheelScreen) {
			wheelKeyWasDown = keyDown;
			return;
		}

		if (minecraft.screen != null) {
			wheelKeyWasDown = keyDown;
			return;
		}

		if (keyDown && !wheelKeyWasDown && !holdWheelSessionActive) {
			InteractionHand hand = FTBIColorSprayPaintWheelOpener.findKitHand(minecraft.player);
			ItemStack stack = minecraft.player.getItemInHand(hand);
			if (stack.getItem() instanceof FTBIColorSprayPaintKitItem kit) {
				holdWheelSessionActive = true;
				FTBIColorSprayPaintWheelOpener.open(minecraft.player, hand, stack, kit.kitType, true);
			}
		}

		wheelKeyWasDown = keyDown;
	}

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		tryOpenWheel(event.getPlayer(), event.getHand(), event);
	}

	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
		tryOpenWheel(event.getPlayer(), event.getHand(), event);
	}

	private static void tryOpenWheel(Player player, InteractionHand hand, Event event) {
		if (!player.level.isClientSide()) {
			return;
		}

		ItemStack stack = player.getItemInHand(hand);
		if (!(stack.getItem() instanceof FTBIColorSprayPaintKitItem kit)) {
			return;
		}

		if (!FTBIColorSprayPaintKitUtil.shouldOpenWheel(player, stack, FTBIColorSprayPaintWheelOpener.hasControlDown())) {
			return;
		}

		if (event instanceof PlayerInteractEvent interactEvent) {
			interactEvent.setCanceled(true);
			interactEvent.setCancellationResult(InteractionResult.SUCCESS);
		}

		FTBIColorSprayPaintWheelOpener.open(player, hand, stack, kit.kitType, false);
	}
}
