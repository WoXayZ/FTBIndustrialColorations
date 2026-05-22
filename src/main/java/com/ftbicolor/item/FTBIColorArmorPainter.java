package com.ftbicolor.item;

import com.ftbicolor.advancement.FTBIColorAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class FTBIColorArmorPainter {
	private FTBIColorArmorPainter() {
	}

	public static boolean tryPaintInventoryArmor(
			ItemStack carriedSprayCan,
			Slot slot,
			ClickAction action,
			Player player,
			DyeColor color
	) {
		if (action != ClickAction.SECONDARY || slot == null) {
			return false;
		}

		ItemStack armorStack = slot.getItem();
		if (!FTBIColorArmorUtil.isQuantumArmor(armorStack)) {
			return false;
		}

		Level level = player.level;
		if (!FTBIColorArmorUtil.setColor(armorStack, color)) {
			return true;
		}

		if (level.isClientSide()) {
			float pitch = 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F;
			level.playSound(
					player,
					player.getX(),
					player.getY(),
					player.getZ(),
					SoundEvents.REDSTONE_TORCH_BURNOUT,
					SoundSource.PLAYERS,
					0.5F,
					pitch
			);
		}

		slot.setChanged();

		if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
			FTBIColorAdvancements.markSprayPaintUsed(serverPlayer, color);
			FTBIColorAdvancements.checkQuantumColoration(serverPlayer);
		}

		return true;
	}
}
