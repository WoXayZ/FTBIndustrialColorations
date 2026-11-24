package com.ftbicolor.client;

import com.ftbicolor.FTBIColorConstants;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public final class FTBIColorKeyBindings {
	public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath(FTBIColorConstants.MOD_ID, "main"));

	public static final KeyMapping SPRAY_PAINT_WHEEL = new KeyMapping(
			"key.ftbicolor.spray_paint_wheel",
			KeyConflictContext.IN_GAME,
			KeyModifier.SHIFT,
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_C,
			CATEGORY
	);

	public static final KeyMapping SPRAY_PAINT_WHEEL_PERSISTENT = new PersistentWheelKey();

	private FTBIColorKeyBindings() {
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

			return controlDown() && leftShiftDown();
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
			return InputConstants.isKeyDown(Minecraft.getInstance().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT);
		}

		private static boolean controlDown() {
			long window = Minecraft.getInstance().getWindow().handle();
			return org.lwjgl.glfw.GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS
					|| org.lwjgl.glfw.GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;
		}
	}
}
