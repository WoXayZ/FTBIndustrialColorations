package com.ftbicolor.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public final class FTBIColorKeyBindings {
	public static final String CATEGORY = "key.categories.ftbicolor";

	public static final KeyMapping SPRAY_PAINT_WHEEL = new KeyMapping(
			"key.ftbicolor.spray_paint_wheel",
			KeyConflictContext.IN_GAME,
			KeyModifier.SHIFT,
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_C,
			CATEGORY
	);

	private static boolean registered;

	private FTBIColorKeyBindings() {
	}

	public static void registerWithClient() {
		if (registered) {
			return;
		}

		net.minecraftforge.client.ClientRegistry.registerKeyBinding(SPRAY_PAINT_WHEEL);
		registered = true;
	}
}
