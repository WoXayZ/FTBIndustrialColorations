package com.ftbicolor.mixin;

import com.ftbicolor.FTBIColor;
import dev.ftb.mods.ftbic.block.ElectricBlock;
import dev.ftb.mods.ftbic.block.ElectricBlockInstance;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElectricBlock.class)
public abstract class ElectricBlockMixin extends Block {
	private ElectricBlockMixin() {
		super(Properties.of(net.minecraft.world.level.material.Material.METAL));
	}

	@Inject(method = "createBlockStateDefinition", at = @At("HEAD"))
	private void ftbicolor$addColorProperty(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
		builder.add(FTBIColor.COLOR);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void ftbicolor$setDefaultColor(ElectricBlockInstance instance, CallbackInfo ci) {
		this.registerDefaultState(this.defaultBlockState().setValue(FTBIColor.COLOR, DyeColor.WHITE));
	}
}
