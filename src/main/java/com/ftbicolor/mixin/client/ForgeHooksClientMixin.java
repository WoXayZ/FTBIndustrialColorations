package com.ftbicolor.mixin.client;

import com.ftbicolor.item.FTBIColorArmorUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeHooksClient.class, remap = false)
public class ForgeHooksClientMixin {
	@Inject(
			method = "getArmorTexture(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;Ljava/lang/String;Lnet/minecraft/world/entity/EquipmentSlot;Ljava/lang/String;)Ljava/lang/String;",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	private static void ftbicolor$quantumArmorTexture(
			Entity entity,
			ItemStack armor,
			String _default,
			EquipmentSlot slot,
			String type,
			CallbackInfoReturnable<String> cir
	) {
		if (FTBIColorArmorUtil.isQuantumArmor(armor)) {
			cir.setReturnValue(FTBIColorArmorUtil.getArmorTexture(armor, slot, type));
		}
	}
}
