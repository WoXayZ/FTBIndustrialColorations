package com.ftbicolor.compat;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.item.FTBIColorSprayPaintActions;
import com.ftbicolor.item.FTBIColorSprayPaintCanItem;
import com.ftbicolor.item.FTBIColorSprayPaintKitColor;
import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import dev.ftb.mods.ftbic.item.SprayPaintCanItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.common.MinecraftForge;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.event.CurioEquipEvent;

import java.util.Optional;

public final class FTBIColorCurios {
	private FTBIColorCurios() {
	}

	public static void init(IEventBus modBus) {
		modBus.addListener(FTBIColorCurios::enqueue);
		MinecraftForge.EVENT_BUS.register(FTBIColorCurios.class);
	}

	private static void enqueue(InterModEnqueueEvent event) {
		InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder(FTBIColorConstants.CURIOS_SLOT)
				.icon(new ResourceLocation(CuriosApi.MODID, "slot/empty_paint_can"))
				.size(1)
				.build());
	}

	@SubscribeEvent
	public static void onEquip(CurioEquipEvent event) {
		if (!FTBIColorConstants.CURIOS_SLOT.equals(event.getSlotContext().identifier())) {
			return;
		}

		if (!(event.getStack().getItem() instanceof FTBIColorSprayPaintKitItem)) {
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
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
		Optional<SlotResult> preferred = CuriosApi.getCuriosHelper().findCurio(entity, FTBIColorConstants.CURIOS_SLOT, 0);
		if (preferred.isPresent() && preferred.get().stack().getItem() instanceof FTBIColorSprayPaintKitItem) {
			return preferred.get().stack();
		}

		return CuriosApi.getCuriosHelper()
				.findFirstCurio(entity, stack -> stack.getItem() instanceof FTBIColorSprayPaintKitItem)
				.map(SlotResult::stack)
				.orElse(ItemStack.EMPTY);
	}

	public static void save(LivingEntity entity, ItemStack stack) {
		Optional<SlotResult> found = CuriosApi.getCuriosHelper().findFirstCurio(entity, candidate -> candidate == stack);
		if (found.isEmpty()) {
			found = CuriosApi.getCuriosHelper().findCurio(entity, FTBIColorConstants.CURIOS_SLOT, 0)
					.filter(result -> result.stack().getItem() instanceof FTBIColorSprayPaintKitItem);
		}

		found.ifPresent(result -> CuriosApi.getCuriosHelper().setEquippedCurio(
				entity,
				result.slotContext().identifier(),
				result.slotContext().index(),
				stack
		));
	}
}
