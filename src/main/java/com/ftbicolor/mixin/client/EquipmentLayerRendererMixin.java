package com.ftbicolor.mixin.client;

import com.ftbicolor.client.FTBIColorArmorAssets;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EquipmentLayerRenderer.class)
public class EquipmentLayerRendererMixin {
	@Inject(
			method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;II)V",
			at = @At("HEAD"),
			cancellable = true
	)
	private void ftbicolor$armorTexture(
			EquipmentClientInfo.LayerType type,
			ResourceKey<EquipmentAsset> asset,
			Model<?> model,
			Object state,
			ItemStack stack,
			PoseStack pose,
			SubmitNodeCollector collector,
			int light,
			int overlay,
			CallbackInfo ci
	) {
		ResourceKey<EquipmentAsset> replacement = FTBIColorArmorAssets.keyFor(stack);
		if (replacement == null || replacement.equals(asset)) {
			return;
		}

		ci.cancel();
		renderWithAsset(type, replacement, model, state, stack, pose, collector, light, overlay);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void renderWithAsset(
			EquipmentClientInfo.LayerType type,
			ResourceKey replacement,
			Model model,
			Object state,
			ItemStack stack,
			PoseStack pose,
			SubmitNodeCollector collector,
			int light,
			int overlay
	) {
		((EquipmentLayerRenderer) (Object) this).renderLayers(type, replacement, model, state, stack, pose, collector, light, overlay);
	}
}
