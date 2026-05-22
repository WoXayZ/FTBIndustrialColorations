package com.ftbicolor.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public final class FTBIColorSprayPaintKitTooltips {
	private FTBIColorSprayPaintKitTooltips() {
	}

	public static Component openWheelHint() {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft == null || minecraft.options == null) {
			return new TranslatableComponent("item.ftbicolor.spray_paint_kit.open_wheel_fallback");
		}

		Component wheelKey = new TranslatableComponent(
				"item.ftbicolor.spray_paint_kit.hotkey_label",
				FTBIColorKeyBindings.SPRAY_PAINT_WHEEL.getTranslatedKeyMessage()
		);
		MutableComponent modifierCombo = new TextComponent("Ctrl+")
				.append(minecraft.options.keyShift.getTranslatedKeyMessage())
				.append("+")
				.append(minecraft.options.keyUse.getTranslatedKeyMessage());
		return new TranslatableComponent("item.ftbicolor.spray_paint_kit.open_wheel", wheelKey, modifierCombo);
	}
}
