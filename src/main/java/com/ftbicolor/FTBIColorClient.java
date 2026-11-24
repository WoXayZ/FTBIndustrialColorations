package com.ftbicolor;

import com.ftbicolor.client.FTBIColorKeyBindings;
import com.ftbicolor.client.FTBIColorSprayPaintKitTooltips;
import com.ftbicolor.platform.FTBIColorClientHooks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = FTBIColorConstants.MOD_ID, value = Dist.CLIENT)
public final class FTBIColorClient {
	static {
		FTBIColorClientHooks.setWheelHint(FTBIColorSprayPaintKitTooltips::openWheelHint);
	}

	private FTBIColorClient() {
	}

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(FTBIColorKeyBindings.SPRAY_PAINT_WHEEL);
		event.register(FTBIColorKeyBindings.SPRAY_PAINT_WHEEL_PERSISTENT);
	}
}
