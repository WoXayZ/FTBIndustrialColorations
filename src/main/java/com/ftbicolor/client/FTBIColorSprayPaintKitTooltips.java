package com.ftbicolor.client;

import com.ftbicolor.platform.FTBIColorText;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class FTBIColorSprayPaintKitTooltips {
	private FTBIColorSprayPaintKitTooltips() {
	}

	public static Component openWheelHint() {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft == null || minecraft.options == null) {
			return FTBIColorText.translatable("item.ftbicolor.spray_paint_kit.open_wheel_fallback");
		}

		Component wheelKey = FTBIColorText.translatable(
				"item.ftbicolor.spray_paint_kit.hotkey_label",
				FTBIColorKeyBindings.SPRAY_PAINT_WHEEL.getTranslatedKeyMessage()
		);
		Component persistentKey = FTBIColorText.translatable(
				"item.ftbicolor.spray_paint_kit.hotkey_label",
				FTBIColorKeyBindings.SPRAY_PAINT_WHEEL_PERSISTENT.getTranslatedKeyMessage()
		);
		return FTBIColorText.translatable("item.ftbicolor.spray_paint_kit.open_wheel", wheelKey, persistentKey);
	}
}
