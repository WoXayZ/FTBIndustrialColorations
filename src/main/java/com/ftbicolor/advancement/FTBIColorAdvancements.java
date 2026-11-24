package com.ftbicolor.advancement;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.item.FTBIColorArmorUtil;
import com.ftbicolor.item.FTBIColorItems;
import com.ftbicolor.platform.FTBIColorAttachments;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public final class FTBIColorAdvancements {
	private static final Identifier[] KIT_RECIPE_IDS = {
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

	public static void checkRecipeUnlocks(ServerPlayer player) {
		if (isAdvancementDone(player, FTBIColorConstants.ADVANCEMENT_UNLOCK_KITS)
				&& isAdvancementDone(player, FTBIColorConstants.ADVANCEMENT_UNLOCK_SELECTOR)
				&& isAdvancementDone(player, FTBIColorConstants.ADVANCEMENT_SO_MANY_COLORS)) {
			return;
		}

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
			if (!FTBIColorArmorUtil.isQuantumArmor(stack) || !FTBIColorArmorUtil.hasExplicitColor(stack)) {
				return false;
			}
		}

		return true;
	}

	private static long getUsedMask(Player player) {
		return player.getData(FTBIColorAttachments.USED_SPRAY_CANS.get());
	}

	private static void setUsedMask(Player player, long mask) {
		player.setData(FTBIColorAttachments.USED_SPRAY_CANS.get(), mask);
	}

	private static boolean canUnlockKitRecipes(Player player) {
		return hasColoredSprayPaintCan(player) || hasFtbicBaseSprayPaintCan(player);
	}

	private static boolean hasColoredSprayPaintCan(Player player) {
		if (isColoredSprayPaintCan(player.getOffhandItem())) {
			return true;
		}

		for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
			ItemStack stack = player.getInventory().getItem(slot);
			if (isColoredSprayPaintCan(stack)) {
				return true;
			}
		}

		return false;
	}

	private static boolean hasFtbicBaseSprayPaintCan(Player player) {
		Item light = BuiltInRegistries.ITEM.getValue(FTBIColorConstants.FTBIC_LIGHT_SPRAY_CAN);
		Item dark = BuiltInRegistries.ITEM.getValue(FTBIColorConstants.FTBIC_DARK_SPRAY_CAN);
		return (light != null && hasItem(player, light)) || (dark != null && hasItem(player, dark));
	}

	private static boolean hasSprayPaintKit(Player player) {
		return hasItem(player, FTBIColorItems.COLORED_SPRAY_PAINT_KIT_PRIMARY.get())
				|| hasItem(player, FTBIColorItems.COLORED_SPRAY_PAINT_KIT_SECONDARY.get());
	}

	private static boolean hasItem(Player player, Item item) {
		if (player.getOffhandItem().is(item)) {
			return true;
		}

		for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
			if (player.getInventory().getItem(slot).is(item)) {
				return true;
			}
		}

		return false;
	}

	private static boolean isColoredSprayPaintCan(ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}

		Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
		return id != null
				&& FTBIColorConstants.MOD_ID.equals(id.getNamespace())
				&& id.getPath().endsWith("_spray_paint_can");
	}

	private static boolean isAdvancementDone(ServerPlayer player, Identifier advancementId) {
		AdvancementHolder advancement = player.level().getServer().getAdvancements().get(advancementId);
		if (advancement == null) {
			return false;
		}

		AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
		return progress.isDone();
	}

	private static void grantRecipes(ServerPlayer player, Identifier... recipeIds) {
		java.util.List<ResourceKey<Recipe<?>>> recipes = new java.util.ArrayList<>();
		for (Identifier id : recipeIds) {
			recipes.add(ResourceKey.create(Registries.RECIPE, id));
		}
		player.awardRecipesByKey(recipes);
	}

	private static void award(ServerPlayer player, Identifier advancementId, String criterion) {
		AdvancementHolder advancement = player.level().getServer().getAdvancements().get(advancementId);
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
