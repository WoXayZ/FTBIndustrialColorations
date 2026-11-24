package com.ftbicolor.mixin;

import com.ftbicolor.FTBIColor;
import dev.ftb.mods.ftbic.block.BurntReinforcedCableBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BurntReinforcedCableBlock.class)
public abstract class BurntReinforcedCableBlockMixin extends Block {
	private BurntReinforcedCableBlockMixin() {
		super(Properties.of());
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void ftbicolor$defaultBurntColor(BlockBehaviour.Properties props, CallbackInfo ci) {
		registerDefaultState(defaultBlockState()
				.setValue(FTBIColor.COLOR, DyeColor.BLACK)
				.setValue(FTBIColor.PAINTED, false));
	}
}
