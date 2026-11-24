package com.ftbicolor.platform;

import com.ftbicolor.FTBIColorConstants;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class FTBIColorAttachments {
	public static final DeferredRegister<AttachmentType<?>> REGISTRY =
			DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FTBIColorConstants.MOD_ID);

	public static final Supplier<AttachmentType<Long>> USED_SPRAY_CANS = REGISTRY.register("used_spray_cans",
			() -> AttachmentType.builder(() -> 0L).serialize(Codec.LONG.fieldOf("value")).copyOnDeath().build());

	private FTBIColorAttachments() {
	}
}
