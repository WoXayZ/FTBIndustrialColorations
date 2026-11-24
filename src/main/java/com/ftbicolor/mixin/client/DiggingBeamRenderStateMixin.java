package com.ftbicolor.mixin.client;

import com.ftbicolor.client.FTBIColorDiggingColor;
import dev.ftb.mods.ftbic.client.renderer.DiggingBeamRenderState;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DiggingBeamRenderState.class)
public abstract class DiggingBeamRenderStateMixin implements FTBIColorDiggingColor {
	@Unique
	private DyeColor ftbicolor$color = DyeColor.WHITE;

	@Override
	public DyeColor ftbicolor$getColor() {
		return ftbicolor$color;
	}

	@Override
	public void ftbicolor$setColor(DyeColor color) {
		ftbicolor$color = color;
	}
}
