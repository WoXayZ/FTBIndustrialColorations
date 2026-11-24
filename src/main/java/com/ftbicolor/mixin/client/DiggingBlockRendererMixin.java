package com.ftbicolor.mixin.client;

import com.ftbicolor.FTBIColor;
import dev.ftb.mods.ftbic.block.entity.machine.DiggingBaseBlockEntity;
import dev.ftb.mods.ftbic.client.DiggingBlockRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DiggingBlockRenderer.class)
public class DiggingBlockRendererMixin {
	@Redirect(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/RenderType;entityCutout(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"
			)
	)
	private RenderType ftbicolor$quarryModelTexture(
			ResourceLocation texture,
			DiggingBaseBlockEntity entity,
			float delta,
			PoseStack matrices,
			MultiBufferSource source,
			int light,
			int overlay
	) {
		String path = texture.getPath();
		if (path.equals("textures/block/quarry_model_light.png") || path.equals("textures/block/quarry_model_dark.png")) {
			BlockState state = entity.getBlockState();
			if (state.hasProperty(FTBIColor.COLOR)) {
				DyeColor color = state.getValue(FTBIColor.COLOR);
				if (color != DyeColor.WHITE && color != DyeColor.BLACK) {
					texture = new ResourceLocation("ftbic", "textures/block/quarry/" + color.getSerializedName() + ".png");
				}
			}
		}
		return RenderType.entityCutout(texture);
	}
}
