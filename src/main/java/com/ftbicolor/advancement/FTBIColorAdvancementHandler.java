package com.ftbicolor.advancement;

import com.ftbicolor.FTBIColorConstants;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = FTBIColorConstants.MOD_ID)
public final class FTBIColorAdvancementHandler {
	private FTBIColorAdvancementHandler() {
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			FTBIColorAdvancements.checkRecipeUnlocks(player);
		}
	}

	@SubscribeEvent
	public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			FTBIColorAdvancements.checkRecipeUnlocks(player);
		}
	}

	@SubscribeEvent
	public static void onItemPickup(ItemEntityPickupEvent.Post event) {
		if (event.getPlayer() instanceof ServerPlayer player) {
			FTBIColorAdvancements.checkRecipeUnlocks(player);
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		if (event.getEntity().level().isClientSide()) {
			return;
		}

		if (event.getEntity().tickCount % 10 != 0 || !(event.getEntity() instanceof ServerPlayer player)) {
			return;
		}

		FTBIColorAdvancements.checkRecipeUnlocks(player);
	}

	@SubscribeEvent
	public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			FTBIColorAdvancements.checkQuantumColoration(player);
		}
	}
}
