package com.ftbicolor.client;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.item.FTBIColorKitSource;
import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = FTBIColorConstants.MOD_ID, value = Dist.CLIENT)
public final class FTBIColorSprayPaintWheelHandler {
	private static boolean wheelKeyWasDown;
	private static boolean persistentKeyWasDown;
	private static boolean holdWheelSessionActive;

	private FTBIColorSprayPaintWheelHandler() {
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Post event) {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.player == null) {
			return;
		}

		boolean keyDown = FTBIColorKeyBindings.SPRAY_PAINT_WHEEL.isDown();
		boolean persistentDown = FTBIColorKeyBindings.SPRAY_PAINT_WHEEL_PERSISTENT.isDown();

		if (!keyDown) {
			holdWheelSessionActive = false;
		}

		if (minecraft.screen instanceof FTBIColorSprayPaintWheelScreen) {
			wheelKeyWasDown = keyDown;
			persistentKeyWasDown = persistentDown;
			return;
		}

		if (minecraft.screen != null) {
			wheelKeyWasDown = keyDown;
			persistentKeyWasDown = persistentDown;
			return;
		}

		if (keyDown && !wheelKeyWasDown && !holdWheelSessionActive) {
			openWheel(minecraft.player, true);
			holdWheelSessionActive = minecraft.screen instanceof FTBIColorSprayPaintWheelScreen;
		} else if (persistentDown && !persistentKeyWasDown) {
			openWheel(minecraft.player, false);
		}

		wheelKeyWasDown = keyDown;
		persistentKeyWasDown = persistentDown;
	}

	private static void openWheel(Player player, boolean holdMode) {
		int source = FTBIColorKitSource.locate(player);
		ItemStack stack = source < 0 ? ItemStack.EMPTY : FTBIColorKitSource.stack(player, source);
		if (stack.getItem() instanceof FTBIColorSprayPaintKitItem kit) {
			FTBIColorSprayPaintWheelOpener.open(player, source, stack, kit.kitType, holdMode);
		}
	}

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		tryOpenWheel(event.getEntity(), event);
	}

	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
		tryOpenWheel(event.getEntity(), event);
	}

	private static void tryOpenWheel(Player player, Event event) {
		if (!player.level().isClientSide() || !FTBIColorKeyBindings.SPRAY_PAINT_WHEEL_PERSISTENT.isDown()) {
			return;
		}

		int source = FTBIColorKitSource.locate(player);
		ItemStack stack = source < 0 ? ItemStack.EMPTY : FTBIColorKitSource.stack(player, source);
		if (!(stack.getItem() instanceof FTBIColorSprayPaintKitItem kit)) {
			return;
		}

		if (event instanceof PlayerInteractEvent.RightClickBlock click) {
			click.setCanceled(true);
			click.setCancellationResult(InteractionResult.SUCCESS);
		} else if (event instanceof PlayerInteractEvent.RightClickItem click) {
			click.setCanceled(true);
			click.setCancellationResult(InteractionResult.SUCCESS);
		}

		if (!(Minecraft.getInstance().screen instanceof FTBIColorSprayPaintWheelScreen)) {
			FTBIColorSprayPaintWheelOpener.open(player, source, stack, kit.kitType, false);
		}
	}
}
