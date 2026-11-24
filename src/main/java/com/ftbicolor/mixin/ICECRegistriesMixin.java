package com.ftbicolor.mixin;

import com.ftbicolor.block.FTBIColorFirebricksBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "com.jjyp.ftbicec.registry.ICECRegistries")
public class ICECRegistriesMixin {
	@Redirect(
			method = "lambda$static$0",
			at = @At(value = "NEW", target = "net/minecraft/world/level/block/Block")
	)
	private static Block ftbicolor$firebricks(BlockBehaviour.Properties properties) {
		return new FTBIColorFirebricksBlock(properties);
	}
}
