package com.ftbicolor.compat;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.item.FTBIColorSprayPaintActions;
import com.ftbicolor.item.FTBIColorSprayPaintCanItem;
import com.ftbicolor.item.FTBIColorSprayPaintKitColor;
import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import dev.ftb.mods.ftbic.item.SprayPaintCanItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.event.CurioCanEquipEvent;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public final class FTBIColorCurios {
	private FTBIColorCurios() {
	}

	public static void init(IEventBus modBus) {
		NeoForge.EVENT_BUS.register(FTBIColorCurios.class);
	}

	@SubscribeEvent
	public static void onEquip(CurioCanEquipEvent event) {
		if (!FTBIColorConstants.CURIOS_SLOT.equals(event.getSlotContext().identifier())) {
			return;
		}

		event.setEquipResult(event.getStack().getItem() instanceof FTBIColorSprayPaintKitItem);
	}

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		paintFromCurio(event);
	}

	private static void paintFromCurio(PlayerInteractEvent.RightClickBlock event) {
		ItemStack held = event.getItemStack();
		if (held.getItem() instanceof FTBIColorSprayPaintKitItem
				|| held.getItem() instanceof FTBIColorSprayPaintCanItem
				|| held.getItem() instanceof SprayPaintCanItem) {
			return;
		}

		ItemStack kit = findKit(event.getEntity());
		if (!(kit.getItem() instanceof FTBIColorSprayPaintKitItem)) {
			return;
		}

		DyeColor color = FTBIColorSprayPaintKitColor.getSelected(kit);
		if (color == null || !FTBIColorSprayPaintKitColor.isCurioActive(kit)) {
			return;
		}

		InteractionResult result = FTBIColorSprayPaintActions.paintBlock(
				kit,
				new UseOnContext(event.getEntity(), event.getHand(), event.getHitVec()),
				color
		);
		if (result == InteractionResult.PASS) {
			return;
		}

		event.setCanceled(true);
		event.setCancellationResult(result);
	}

	public static boolean tryPaintInventoryArmor(Slot slot, ClickAction action, Player player) {
		ItemStack kit = findKit(player);
		if (!(kit.getItem() instanceof FTBIColorSprayPaintKitItem)) {
			return false;
		}

		DyeColor color = FTBIColorSprayPaintKitColor.getSelected(kit);
		if (color == null || !FTBIColorSprayPaintKitColor.isCurioActive(kit)) {
			return false;
		}

		return FTBIColorSprayPaintActions.paintInventoryArmor(kit, slot, action, player, color);
	}

	public static ItemStack findKit(LivingEntity entity) {
		ICuriosItemHandler handler = CuriosApi.getCuriosInventoryOrNull(entity);
		if (handler == null) {
			return ItemStack.EMPTY;
		}

		return handler.findFirstCurio(stack -> stack.getItem() instanceof FTBIColorSprayPaintKitItem)
				.map(SlotResult::stack)
				.orElse(ItemStack.EMPTY);
	}

	public static void save(LivingEntity entity, ItemStack stack) {
		ICuriosItemHandler handler = CuriosApi.getCuriosInventoryOrNull(entity);
		if (handler == null) {
			return;
		}

		handler.findFirstCurio(candidate -> candidate == stack).ifPresent(result -> handler.setEquippedCurio(
				result.slotContext().identifier(),
				result.slotContext().index(),
				stack
		));
	}
}
