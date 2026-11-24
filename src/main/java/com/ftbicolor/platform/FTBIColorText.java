package com.ftbicolor.platform;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class FTBIColorText {
	private FTBIColorText() {
	}

	public static MutableComponent translatable(String key, Object... args) {
		return Component.translatable(key, args);
	}

	public static MutableComponent literal(String text) {
		return Component.literal(text);
	}

	public static Component empty() {
		return Component.empty();
	}
}
