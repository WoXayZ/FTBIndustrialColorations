package com.ftbicolor.client;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.block.FTBIColorBlockStates;
import com.ftbicolor.item.FTBIColorArmorUtil;
import com.ftbicolor.item.FTBIColorSprayPaintKitColor;
import com.ftbicolor.platform.FTBIColorDye;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import org.jspecify.annotations.Nullable;

@EventBusSubscriber(modid = FTBIColorConstants.MOD_ID, value = Dist.CLIENT)
public final class FTBIColorItemModels {
	private FTBIColorItemModels() {
	}

	@SubscribeEvent
	public static void registerTint(RegisterColorHandlersEvent.ItemTintSources event) {
		event.register(Identifier.fromNamespaceAndPath(FTBIColorConstants.MOD_ID, "selected_color"), SelectedColorTint.MAP_CODEC);
	}

	@SubscribeEvent
	public static void registerArmorColor(RegisterRangeSelectItemModelPropertyEvent event) {
		event.register(Identifier.fromNamespaceAndPath(FTBIColorConstants.MOD_ID, "armor_color"), ArmorColorProperty.MAP_CODEC);
		event.register(Identifier.fromNamespaceAndPath(FTBIColorConstants.MOD_ID, "picked_color"), PickedColorProperty.MAP_CODEC);
	}

	public record SelectedColorTint() implements ItemTintSource {
		public static final MapCodec<SelectedColorTint> MAP_CODEC = MapCodec.unit(new SelectedColorTint());

		@Override
		public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
			DyeColor color = FTBIColorSprayPaintKitColor.getSelected(stack);
			if (color == null) {
				return -1;
			}
			return ARGB.opaque(FTBIColorDye.opaqueRgb(color));
		}

		@Override
		public MapCodec<SelectedColorTint> type() {
			return MAP_CODEC;
		}
	}

	public record ArmorColorProperty() implements RangeSelectItemModelProperty {
		public static final MapCodec<ArmorColorProperty> MAP_CODEC = MapCodec.unit(new ArmorColorProperty());

		@Override
		public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
			if (!FTBIColorArmorUtil.hasExplicitColor(stack)) {
				return -1.0F;
			}
			return FTBIColorArmorUtil.getColor(stack).getId();
		}

		@Override
		public MapCodec<ArmorColorProperty> type() {
			return MAP_CODEC;
		}
	}

	public record PickedColorProperty() implements RangeSelectItemModelProperty {
		public static final MapCodec<PickedColorProperty> MAP_CODEC = MapCodec.unit(new PickedColorProperty());

		@Override
		public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
			DyeColor color = FTBIColorBlockStates.pickedColor(stack);
			return color == null ? -1.0F : color.getId();
		}

		@Override
		public MapCodec<PickedColorProperty> type() {
			return MAP_CODEC;
		}
	}
}
