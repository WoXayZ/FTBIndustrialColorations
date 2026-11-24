package com.ftbicolor.client;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.item.FTBIColorKitSource;
import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import net.minecraft.client.Minecraft;
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
	private static boolean persistentKeyWasDown;
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
		if (!player.level.isClientSide() || !FTBIColorKeyBindings.SPRAY_PAINT_WHEEL_PERSISTENT.isDown()) {
			return;
		}

		int source = FTBIColorKitSource.locate(player);
		ItemStack stack = source < 0 ? ItemStack.EMPTY : FTBIColorKitSource.stack(player, source);
		if (!(stack.getItem() instanceof FTBIColorSprayPaintKitItem kit)) {
			return;
		}

		if (event instanceof PlayerInteractEvent interactEvent) {
			interactEvent.setCanceled(true);
			interactEvent.setCancellationResult(InteractionResult.SUCCESS);
		}

		if (!(Minecraft.getInstance().screen instanceof FTBIColorSprayPaintWheelScreen)) {
			FTBIColorSprayPaintWheelOpener.open(player, source, stack, kit.kitType, false);
		}
	}
}
