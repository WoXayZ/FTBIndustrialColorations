package com.ftbicolor.advancement;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.item.FTBIColorArmorUtil;
import com.ftbicolor.item.FTBIColorItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.registries.ForgeRegistries;

public final class FTBIColorAdvancements {
	private static final ResourceLocation[] KIT_RECIPE_IDS = {
			FTBIColorConstants.RECIPE_KIT_PRIMARY,
			FTBIColorConstants.RECIPE_KIT_SECONDARY,
	};

	private FTBIColorAdvancements() {
	}

	public static void markSprayPaintUsed(ServerPlayer player, DyeColor color) {
		long mask = getUsedMask(player);
		long bit = 1L << color.getId();
		if ((mask & bit) != 0L) {
			return;
		}

		setUsedMask(player, mask | bit);
		award(player, FTBIColorConstants.ADVANCEMENT_COLORFUL_WORLD, color.getName());
	}

	public static void markFtbicSprayPaintUsed(ServerPlayer player, boolean dark) {
		markSprayPaintUsed(player, dark ? DyeColor.BLACK : DyeColor.WHITE);
	}

	public static void checkRecipeUnlocks(ServerPlayer player) {
		if (canUnlockKitRecipes(player)) {
			if (!isAdvancementDone(player, FTBIColorConstants.ADVANCEMENT_UNLOCK_KITS)) {
				award(player, FTBIColorConstants.ADVANCEMENT_UNLOCK_KITS, "has_colored_spray_paint_can");
			}

			grantRecipes(player, KIT_RECIPE_IDS);
		}

		if (hasSprayPaintKit(player) && !isAdvancementDone(player, FTBIColorConstants.ADVANCEMENT_UNLOCK_SELECTOR)) {
			award(player, FTBIColorConstants.ADVANCEMENT_UNLOCK_SELECTOR, "has_spray_paint_kit");
			grantRecipes(player, FTBIColorConstants.RECIPE_SELECTOR);
		}

		if (hasItem(player, FTBIColorItems.COLORED_SPRAY_PAINT_SELECTOR.get())
				&& !isAdvancementDone(player, FTBIColorConstants.ADVANCEMENT_SO_MANY_COLORS)) {
			award(player, FTBIColorConstants.ADVANCEMENT_SO_MANY_COLORS, "crafted_selector");
		}
	}

	public static void checkQuantumColoration(Player player) {
		if (!(player instanceof ServerPlayer serverPlayer) || !hasFullColoredQuantumSet(player)) {
			return;
		}

		award(serverPlayer, FTBIColorConstants.ADVANCEMENT_QUANTUM_COLORATION, "full_set");
	}

	private static boolean hasFullColoredQuantumSet(Player player) {
		for (var slot : FTBIColorConstants.ARMOR_EQUIPMENT_SLOTS) {
			ItemStack stack = player.getItemBySlot(slot);
			if (!FTBIColorArmorUtil.isQuantumArmor(stack) || !stack.hasTag()) {
				return false;
			}

			CompoundTag tag = stack.getTag();
			if (tag == null || !tag.contains(FTBIColorConstants.ARMOR_COLOR_NBT)) {
				return false;
			}
		}

		return true;
	}

	private static long getUsedMask(Player player) {
		return player.getPersistentData().getLong(FTBIColorConstants.PLAYER_USED_SPRAY_CANS_NBT);
	}

	private static void setUsedMask(Player player, long mask) {
		player.getPersistentData().putLong(FTBIColorConstants.PLAYER_USED_SPRAY_CANS_NBT, mask);
	}

	private static boolean canUnlockKitRecipes(Player player) {
		return hasColoredSprayPaintCan(player) || hasFtbicBaseSprayPaintCan(player);
	}

	private static boolean hasColoredSprayPaintCan(Player player) {
		for (ItemStack stack : player.getInventory().items) {
			if (isColoredSprayPaintCan(stack)) {
				return true;
			}
		}

		return false;
	}

	private static boolean hasFtbicBaseSprayPaintCan(Player player) {
		Item light = ForgeRegistries.ITEMS.getValue(FTBIColorConstants.FTBIC_LIGHT_SPRAY_CAN);
		Item dark = ForgeRegistries.ITEMS.getValue(FTBIColorConstants.FTBIC_DARK_SPRAY_CAN);
		return (light != null && hasItem(player, light)) || (dark != null && hasItem(player, dark));
	}

	private static boolean hasSprayPaintKit(Player player) {
		return hasItem(player, FTBIColorItems.COLORED_SPRAY_PAINT_KIT_PRIMARY.get())
				|| hasItem(player, FTBIColorItems.COLORED_SPRAY_PAINT_KIT_SECONDARY.get());
	}

	private static boolean hasItem(Player player, Item item) {
		for (ItemStack stack : player.getInventory().items) {
			if (stack.is(item)) {
				return true;
			}
		}

		return false;
	}

	private static boolean isColoredSprayPaintCan(ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}

		ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
		return id != null
				&& FTBIColorConstants.MOD_ID.equals(id.getNamespace())
				&& id.getPath().endsWith("_spray_paint_can");
	}

	private static boolean isAdvancementDone(ServerPlayer player, ResourceLocation advancementId) {
		Advancement advancement = player.getServer().getAdvancements().getAdvancement(advancementId);
		if (advancement == null) {
			return false;
		}

		AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
		return progress.isDone();
	}

	private static void grantRecipes(ServerPlayer player, ResourceLocation... recipeIds) {
		var manager = player.getServer().getRecipeManager();
		java.util.List<Recipe<?>> recipes = new java.util.ArrayList<>();
		for (ResourceLocation id : recipeIds) {
			manager.byKey(id).ifPresent(recipes::add);
		}
		if (!recipes.isEmpty()) {
			player.awardRecipes(recipes);
		}
	}

	private static void award(ServerPlayer player, ResourceLocation advancementId, String criterion) {
		Advancement advancement = player.getServer().getAdvancements().getAdvancement(advancementId);
		if (advancement == null) {
			return;
		}

		AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
		if (progress.isDone()) {
			return;
		}

		player.getAdvancements().award(advancement, criterion);
	}
}
