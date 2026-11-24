package com.ftbicolor;

import com.ftbicolor.block.FTBIColorBlockPainter;
import com.ftbicolor.block.FTBIColorBlocks;
import com.ftbicolor.client.FTBIColorKeyBindings;
import com.ftbicolor.client.FTBIColorPickedModel;
import com.ftbicolor.client.FTBIColorSprayPaintKitTooltips;
import com.ftbicolor.item.FTBIColorArmorUtil;
import com.ftbicolor.item.FTBIColorItems;
import com.ftbicolor.item.FTBIColorSprayPaintKitColor;
import com.ftbicolor.platform.FTBIColorClientHooks;
import com.ftbicolor.platform.FTBIColorDye;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FTBIColorConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class FTBIColorClient {
	static {
		FTBIColorClientHooks.setWheelHint(FTBIColorSprayPaintKitTooltips::openWheelHint);
	}

	private FTBIColorClient() {
	}

	@SubscribeEvent
	public static void registerRenderLayers(FMLClientSetupEvent event) {
		event.enqueueWork(() ->
				ItemBlockRenderTypes.setRenderLayer(FTBIColorBlocks.COLORED_REINFORCED_GLASS.get(), RenderType.cutout()));
	}

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(FTBIColorKeyBindings.SPRAY_PAINT_WHEEL);
		event.register(FTBIColorKeyBindings.SPRAY_PAINT_WHEEL_PERSISTENT);
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		event.register(FTBIColorClient::kitTintColor, kitItems());
	}

	@SubscribeEvent
	public static void registerItemProperties(ModelEvent.RegisterAdditional event) {
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

		for (String cable : new String[] {"lv_cable", "mv_cable", "hv_cable", "ev_cable", "iv_cable"}) {
			for (DyeColor color : DyeColor.values()) {
				event.register(new ResourceLocation("ftbic", "item/cable/" + color.getSerializedName() + "/" + cable));
			}
		}
	}

	@SubscribeEvent
	public static void bakePickedColorModels(ModelEvent.BakingCompleted event) {
		var registry = event.getModels();
		for (Item item : ForgeRegistries.ITEMS) {
			if (!(item instanceof BlockItem blockItem)) {
				continue;
			}
			Block block = blockItem.getBlock();
			if (!FTBIColorBlockPainter.isPaintable(block.defaultBlockState())) {
				continue;
			}
			ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
			if (id == null || !isPickedColorNamespace(id.getNamespace())) {
				continue;
			}
			ModelResourceLocation inventory = new ModelResourceLocation(id, "inventory");
			BakedModel model = registry.get(inventory);
			if (model != null && !(model instanceof FTBIColorPickedModel)) {
				registry.put(inventory, new FTBIColorPickedModel(model));
			}
		}
	}

	private static boolean isPickedColorNamespace(String namespace) {
		return FTBIColorConstants.FTBIC_MOD_ID.equals(namespace)
				|| FTBIColorConstants.ICEG_MOD_ID.equals(namespace)
				|| FTBIColorConstants.ZAPS_MOD_ID.equals(namespace);
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

		return FTBIColorDye.opaqueRgb(color);
	}
}
