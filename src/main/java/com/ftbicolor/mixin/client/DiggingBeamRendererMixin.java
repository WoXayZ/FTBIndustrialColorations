package com.ftbicolor.mixin.client;

import com.ftbicolor.FTBIColor;
import com.ftbicolor.client.FTBIColorDiggingColor;
import com.ftbicolor.client.FTBIColorQuarryTextures;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbic.block.entity.machine.DiggingBaseBlockEntity;
import dev.ftb.mods.ftbic.client.renderer.DiggingBeamRenderState;
import dev.ftb.mods.ftbic.client.renderer.DiggingBeamRenderer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DiggingBeamRenderer.class)
public class DiggingBeamRendererMixin {
	@Unique
	private DyeColor ftbicolor$color = DyeColor.WHITE;

	@Inject(method = "extractRenderState", at = @At("RETURN"))
	private void ftbicolor$captureColor(
			DiggingBaseBlockEntity be,
			DiggingBeamRenderState state,
			float partialTick,
			Vec3 cameraPos,
			ModelFeatureRenderer.CrumblingOverlay crumbling,
			CallbackInfo ci
	) {
		BlockState block = be.getBlockState();
		DyeColor color = block.hasProperty(FTBIColor.COLOR) ? block.getValue(FTBIColor.COLOR) : DyeColor.WHITE;
		((FTBIColorDiggingColor) state).ftbicolor$setColor(color);
	}

	@Inject(method = "renderGantry", at = @At("HEAD"))
	private void ftbicolor$useCapturedColor(DiggingBeamRenderState state, PoseStack pose, SubmitNodeCollector buffers, CallbackInfo ci) {
		ftbicolor$color = ((FTBIColorDiggingColor) state).ftbicolor$getColor();
	}

	@WrapOperation(
			method = "renderGantry",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModelPart(Lnet/minecraft/client/model/geom/ModelPart;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"
			)
	)
	private void ftbicolor$coloredGantry(
			SubmitNodeCollector buffers,
			ModelPart part,
			PoseStack pose,
			RenderType type,
			int light,
			int overlay,
			TextureAtlasSprite sprite,
			Operation<Void> original
	) {
		original.call(buffers, part, pose, FTBIColorQuarryTextures.model(ftbicolor$color), light, overlay, sprite);
	}
}
