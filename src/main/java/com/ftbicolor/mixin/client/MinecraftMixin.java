package com.ftbicolor.mixin.client;

import com.ftbicolor.FTBIColorPick;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(method = "pickBlockOrEntity", at = @At("HEAD"))
	private void ftbicolor$beginCtrlPick(CallbackInfo ci) {
		Minecraft minecraft = (Minecraft) (Object) this;
		boolean creative = minecraft.player != null && minecraft.player.getAbilities().instabuild;
		FTBIColorPick.setCtrlPick(creative && minecraft.hasControlDown());
	}

	@Inject(method = "pickBlockOrEntity", at = @At("RETURN"))
	private void ftbicolor$endCtrlPick(CallbackInfo ci) {
		FTBIColorPick.setCtrlPick(false);
	}
}
