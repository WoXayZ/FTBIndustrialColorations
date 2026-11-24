package com.ftbicolor.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
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

	public static final KeyMapping SPRAY_PAINT_WHEEL_PERSISTENT = new PersistentWheelKey();

	private static boolean registered;

	private FTBIColorKeyBindings() {
	}

	public static void registerWithClient() {
		if (registered) {
			return;
		}

		net.minecraftforge.client.ClientRegistry.registerKeyBinding(SPRAY_PAINT_WHEEL);
		net.minecraftforge.client.ClientRegistry.registerKeyBinding(SPRAY_PAINT_WHEEL_PERSISTENT);
		registered = true;
	}

	private static final class PersistentWheelKey extends KeyMapping {
		private PersistentWheelKey() {
			super(
					"key.ftbicolor.spray_paint_wheel_persistent",
					KeyConflictContext.IN_GAME,
					KeyModifier.SHIFT,
					InputConstants.Type.MOUSE,
					GLFW.GLFW_MOUSE_BUTTON_RIGHT,
					CATEGORY
			);
		}

		@Override
		public boolean isDown() {
			if (!super.isDown()) {
				return false;
			}

			if (!usesDefaultCombo()) {
				return true;
			}

			return Screen.hasControlDown() && leftShiftDown();
		}

		@Override
		public net.minecraft.network.chat.Component getTranslatedKeyMessage() {
			if (usesDefaultCombo()) {
				return com.ftbicolor.platform.FTBIColorText.translatable("key.ftbicolor.spray_paint_wheel_persistent.binding");
			}

			return super.getTranslatedKeyMessage();
		}

		private boolean usesDefaultCombo() {
			return getKeyModifier() == KeyModifier.SHIFT
					&& getKey().getType() == InputConstants.Type.MOUSE
					&& getKey().getValue() == GLFW.GLFW_MOUSE_BUTTON_RIGHT;
		}

		private static boolean leftShiftDown() {
			long window = Minecraft.getInstance().getWindow().getWindow();
			return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT);
		}
	}
}
