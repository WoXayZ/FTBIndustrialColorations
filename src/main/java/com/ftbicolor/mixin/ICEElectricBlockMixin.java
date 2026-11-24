package com.ftbicolor.mixin;

import com.ftbicolor.block.FTBIColorBlockStates;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.jjyp.ftbicec.machine.ExpansionGeneratorBlock")
public abstract class ICEElectricBlockMixin {
	@Inject(method = "createBlockStateDefinition", at = @At("HEAD"))
	private void ftbicolor$addColorProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
		FTBIColorBlockStates.defineColor(builder);
	}
}
