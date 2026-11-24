package com.ftbicolor.mixin;

import com.ftbicolor.FTBIColor;
import com.ftbicolor.block.FTBIColorBlockStates;
import dev.ftb.mods.ftbic.block.BaseCableBlock;
import dev.ftb.mods.ftbic.block.BurntReinforcedCableBlock;
import dev.ftb.mods.ftbic.block.CableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseCableBlock.class)
public abstract class BaseCableBlockMixin extends Block {
	private BaseCableBlockMixin() {
		super(Properties.of());
	}

	@Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
	private void ftbicolor$addColorProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
		if ((Object) this instanceof CableBlock || (Object) this instanceof BurntReinforcedCableBlock) {
			FTBIColorBlockStates.defineColor(builder);
			builder.add(FTBIColor.PAINTED);
		}
	}
}
