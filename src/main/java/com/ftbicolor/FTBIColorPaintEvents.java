package com.ftbicolor;

import com.ftbicolor.block.FTBIColorBlockPainter;
import com.ftbicolor.item.FTBIColorSprayPaintActions;
import com.ftbicolor.item.FTBIColorSprayPaintCanItem;
import com.ftbicolor.item.FTBIColorSprayPaintKitColor;
import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import com.ftbicolor.platform.FTBIColorDye;
import dev.ftb.mods.ftbic.item.SprayPaintCanItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = FTBIColorConstants.MOD_ID)
public final class FTBIColorPaintEvents {
	private FTBIColorPaintEvents() {
	}

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();
		DyeColor color = colorOf(stack);
		if (color == null) {
			return;
		}

		BlockState state = event.getLevel().getBlockState(event.getPos());
		if (!FTBIColorBlockPainter.isPaintable(state)) {
			return;
		}

		InteractionResult result = FTBIColorSprayPaintActions.paintBlock(
				stack,
				new UseOnContext(event.getEntity(), event.getHand(), event.getHitVec()),
				color
		);
		if (result == InteractionResult.PASS) {
			return;
		}

		event.setCanceled(true);
		event.setCancellationResult(result);
	}

	private static DyeColor colorOf(ItemStack stack) {
		if (stack.getItem() instanceof FTBIColorSprayPaintKitItem) {
			return FTBIColorSprayPaintKitColor.getSelected(stack);
		}

		if (stack.getItem() instanceof FTBIColorSprayPaintCanItem can) {
			return can.color;
		}

		if (stack.getItem() instanceof SprayPaintCanItem can) {
			return FTBIColorDye.fromVanillaCan(can.dark);
		}

		return null;
	}
}
