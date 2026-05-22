package com.ftbicolor.advancement;

import com.ftbicolor.FTBIColorConstants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FTBIColorConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class FTBIColorAdvancementHandler {
	private FTBIColorAdvancementHandler() {
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getPlayer() instanceof ServerPlayer player) {
			FTBIColorAdvancements.checkRecipeUnlocks(player);
		}
	}

	@SubscribeEvent
	public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
		if (event.getPlayer() instanceof ServerPlayer player) {
			FTBIColorAdvancements.checkRecipeUnlocks(player);
		}
	}

	@SubscribeEvent
	public static void onItemPickup(EntityItemPickupEvent event) {
		if (event.getPlayer() instanceof ServerPlayer player) {
			FTBIColorAdvancements.checkRecipeUnlocks(player);
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END || event.player.level.isClientSide()) {
			return;
		}

		if (event.player.tickCount % 10 != 0 || !(event.player instanceof ServerPlayer player)) {
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
