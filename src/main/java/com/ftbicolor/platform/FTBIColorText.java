package com.ftbicolor.platform;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public final class FTBIColorText {
	private FTBIColorText() {
	}

	public static MutableComponent translatable(String key, Object... args) {
		return new TranslatableComponent(key, args);
	}

	public static MutableComponent literal(String text) {
		return new TextComponent(text);
	}

	public static Component empty() {
		return TextComponent.EMPTY;
	}
}
