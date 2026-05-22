package com.ftbicolor;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathResourcePack;

import java.io.IOException;
import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = FTBIColorConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class FTBIColorResourcePack {
	private static final String PACK_NAME = FTBIColorConstants.MOD_ID + "_override";
	private static final String PACK_ID = "builtin/" + PACK_NAME;

	private FTBIColorResourcePack() {
	}

	@SubscribeEvent
	public static void onAddPackFinders(AddPackFindersEvent event) {
		if (event.getPackType() != PackType.CLIENT_RESOURCES) {
			return;
		}

		try {
			IModFile modFile = ModList.get().getModFileById(FTBIColorConstants.MOD_ID).getFile();
			Path source = modFile.findResource(PACK_NAME);
			PathResourcePack pack = new PathResourcePack(PACK_ID, source);
			PackMetadataSection metadata = pack.getMetadataSection(PackMetadataSection.SERIALIZER);

			if (metadata == null) {
				return;
			}

			event.addRepositorySource((packConsumer, packConstructor) ->
					packConsumer.accept(packConstructor.create(
							PACK_ID,
							new TextComponent("FTB Industrial Colorations Overrides"),
							true,
							() -> pack,
							metadata,
							Pack.Position.TOP,
							PackSource.BUILT_IN,
							false
					))
			);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to register ftbicolor override resource pack", ex);
		}
	}
}
