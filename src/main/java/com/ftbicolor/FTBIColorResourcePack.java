package com.ftbicolor;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = FTBIColorConstants.MOD_ID)
public final class FTBIColorResourcePack {
	private static final String PACK_NAME = FTBIColorConstants.MOD_ID + "_override";

	private FTBIColorResourcePack() {
	}

	@SubscribeEvent
	public static void onAddPackFinders(AddPackFindersEvent event) {
		if (event.getPackType() != PackType.CLIENT_RESOURCES) {
			return;
		}

		event.addPackFinders(
				Identifier.fromNamespaceAndPath(FTBIColorConstants.MOD_ID, PACK_NAME),
				PackType.CLIENT_RESOURCES,
				Component.literal("FTB Industrial Colorations"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
	}
}
