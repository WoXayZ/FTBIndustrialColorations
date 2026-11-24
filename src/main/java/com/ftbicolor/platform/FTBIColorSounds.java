package com.ftbicolor.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class FTBIColorSounds {
	private FTBIColorSounds() {
	}

	public static void playOnBlock(Level level, Player player, BlockPos pos) {
		level.playSound(player, pos, SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS, 0.5F, pitch(level));
	}

	public static void playOnPlayer(Level level, Player player) {
		level.playSound(
				player,
				player.getX(),
				player.getY(),
				player.getZ(),
				SoundEvents.REDSTONE_TORCH_BURNOUT,
				SoundSource.PLAYERS,
				0.5F,
				pitch(level)
		);
	}

	private static float pitch(Level level) {
		return 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F;
	}
}
