package com.ftbicolor.block;

import com.ftbicolor.FTBIColor;
import com.ftbicolor.FTBIColorPick;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public final class FTBIColorBlockStates {
	private FTBIColorBlockStates() {
	}

	public static void defineColor(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FTBIColor.COLOR);
	}

	public static BlockState withColor(BlockState state, DyeColor color) {
		return state.setValue(FTBIColor.COLOR, color);
	}

	public static final String ITEM_COLOR_TAG = "ftbicolor_color";

	public static ItemStack originalStack(Supplier<? extends Block> original) {
		Item item = original.get().asItem();
		return item == null ? ItemStack.EMPTY : new ItemStack(item);
	}

	public static ItemStack ctrlPickedStack(ItemStack stack, BlockState state) {
		return FTBIColorPick.isCtrlPick() ? withPickedColor(stack, state) : stack;
	}

	public static ItemStack withPickedColor(ItemStack stack, BlockState state) {
		if (stack.isEmpty() || !state.hasProperty(FTBIColor.COLOR)) {
			return stack;
		}
		BlockState defaults = state.getBlock().defaultBlockState();
		boolean sameColor = defaults.getValue(FTBIColor.COLOR) == state.getValue(FTBIColor.COLOR);
		boolean samePaint = !state.hasProperty(FTBIColor.PAINTED)
				|| defaults.getValue(FTBIColor.PAINTED) == state.getValue(FTBIColor.PAINTED);
		if (sameColor && samePaint) {
			return stack;
		}
		stack.getOrCreateTag().putString(ITEM_COLOR_TAG, state.getValue(FTBIColor.COLOR).getSerializedName());
		return stack;
	}

	@Nullable
	public static DyeColor pickedColor(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag == null || !tag.contains(ITEM_COLOR_TAG)) {
			return null;
		}
		return DyeColor.byName(tag.getString(ITEM_COLOR_TAG), null);
	}
}
