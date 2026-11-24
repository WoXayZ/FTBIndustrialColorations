package com.ftbicolor.platform;

import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public final class FTBIColorClientHooks {
	private static Supplier<Component> wheelHint = () -> null;

	private FTBIColorClientHooks() {
	}

	public static void setWheelHint(Supplier<Component> wheelHint) {
		FTBIColorClientHooks.wheelHint = wheelHint;
	}

	public static Component wheelHint() {
		return wheelHint.get();
	}
}
