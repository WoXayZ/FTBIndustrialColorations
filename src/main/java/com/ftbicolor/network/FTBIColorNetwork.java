package com.ftbicolor.network;

import com.ftbicolor.FTBIColorConstants;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = FTBIColorConstants.MOD_ID)
public final class FTBIColorNetwork {
	private FTBIColorNetwork() {
	}

	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event) {
		event.registrar(FTBIColorConstants.MOD_ID).playToServer(
				FTBIColorSetSprayPaintKitColorPacket.TYPE,
				FTBIColorSetSprayPaintKitColorPacket.STREAM_CODEC,
				FTBIColorSetSprayPaintKitColorPacket::handle
		);
	}

	public static void sendToServer(CustomPacketPayload payload) {
		ClientPacketDistributor.sendToServer(payload);
	}
}
