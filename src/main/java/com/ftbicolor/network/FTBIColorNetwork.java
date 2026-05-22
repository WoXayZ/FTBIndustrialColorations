package com.ftbicolor.network;

import com.ftbicolor.FTBIColorConstants;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class FTBIColorNetwork {
	private static final String PROTOCOL = "1";

	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
			FTBIColorConstants.modResource("main"),
			() -> PROTOCOL,
			PROTOCOL::equals,
			PROTOCOL::equals
	);

	private FTBIColorNetwork() {
	}

	public static void register() {
		CHANNEL.messageBuilder(FTBIColorSetSprayPaintKitColorPacket.class, 0)
				.encoder(FTBIColorSetSprayPaintKitColorPacket::encode)
				.decoder(FTBIColorSetSprayPaintKitColorPacket::decode)
				.consumer(FTBIColorSetSprayPaintKitColorPacket::handle)
				.add();
	}
}
