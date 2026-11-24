package com.ftbicolor.mixin;

import com.ftbicolor.FTBIColor;
import dev.ftb.mods.ftbic.block.CableBlock;
import dev.ftb.mods.ftbic.util.EnergyTier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CableBlock.class)
public abstract class CableBlockMixin extends Block {
	private CableBlockMixin() {
		super(Properties.of(Material.METAL));
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void ftbicolor$defaultCableColor(EnergyTier tier, int border, SoundType soundType, CallbackInfo ci) {
		DyeColor color = tier == EnergyTier.IV ? DyeColor.WHITE : DyeColor.BLACK;
		registerDefaultState(defaultBlockState()
				.setValue(FTBIColor.COLOR, color)
				.setValue(FTBIColor.PAINTED, false));
	}
}
