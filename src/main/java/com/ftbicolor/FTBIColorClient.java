package com.ftbicolor;

import com.ftbicolor.block.FTBIColorBlocks;
import com.ftbicolor.client.FTBIColorKeyBindings;
import com.ftbicolor.item.FTBIColorArmorUtil;
import com.ftbicolor.item.FTBIColorItems;
import com.ftbicolor.item.FTBIColorSprayPaintKitColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FTBIColorConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class FTBIColorClient {
	private FTBIColorClient() {
	}

	@SubscribeEvent
	public static void registerRenderLayers(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			FTBIColorKeyBindings.registerWithClient();
			ItemBlockRenderTypes.setRenderLayer(FTBIColorBlocks.COLORED_REINFORCED_GLASS.get(), RenderType.cutout());
		});
	}

	@SubscribeEvent
	public static void registerItemColors(ColorHandlerEvent.Item event) {
		event.getItemColors().register(FTBIColorClient::kitTintColor, kitItems());
	}

	@SubscribeEvent
	public static void registerItemProperties(ModelRegistryEvent event) {
		for (var quantumId : FTBIColorConstants.QUANTUM_ARMOR_ITEMS) {
			Item item = ForgeRegistries.ITEMS.getValue(quantumId);
			if (item != null) {
				ItemProperties.register(item, FTBIColorConstants.ITEM_PROPERTY_COLOR_ID,
						(stack, level, entity, seed) -> FTBIColorArmorUtil.getColorProperty(stack));
			}
		}

		for (Item kitItem : kitItems()) {
			ItemProperties.register(kitItem, FTBIColorConstants.ITEM_PROPERTY_HAS_COLOR_ID,
					(stack, level, entity, seed) -> FTBIColorSprayPaintKitColor.getSelected(stack) != null ? 1.0F : 0.0F);
		}
	}

	private static Item[] kitItems() {
		return new Item[] {
				FTBIColorItems.COLORED_SPRAY_PAINT_KIT_PRIMARY.get(),
				FTBIColorItems.COLORED_SPRAY_PAINT_KIT_SECONDARY.get(),
				FTBIColorItems.COLORED_SPRAY_PAINT_SELECTOR.get(),
		};
	}

	static int kitTintColor(ItemStack stack, int tintIndex) {
		if (tintIndex != 1) {
			return -1;
		}

		DyeColor color = FTBIColorSprayPaintKitColor.getSelected(stack);
		if (color == null) {
			return -1;
		}

		float[] channels = color.getTextureDiffuseColors();
		int r = Mth.clamp((int) (channels[0] * 255.0F), 0, 255);
		int g = Mth.clamp((int) (channels[1] * 255.0F), 0, 255);
		int b = Mth.clamp((int) (channels[2] * 255.0F), 0, 255);
		return 0xFF000000 | (r << 16) | (g << 8) | b;
	}
}
