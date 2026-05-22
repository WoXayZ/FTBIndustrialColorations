package com.ftbicolor.item;

import com.ftbicolor.block.ColorPainter;
import dev.ftb.mods.ftbic.FTBIC;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ColoredSprayPaintCanItem extends Item {
	public final DyeColor color;

	public ColoredSprayPaintCanItem(DyeColor color) {
		super(new Properties().stacksTo(1).tab(FTBIC.TAB));
		this.color = color;
	}

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		BlockState state = context.getLevel().getBlockState(context.getClickedPos());

		if (ColorPainter.isPaintable(state)) {
			boolean painted = ColorPainter.paint(state, context.getLevel(), context.getClickedPos(), color);

			if (painted && context.getLevel().isClientSide()) {
				float pitch = 2.6F + (context.getLevel().random.nextFloat() - context.getLevel().random.nextFloat()) * 0.8F;
				context.getLevel().playSound(
						context.getPlayer(),
						context.getClickedPos(),
						SoundEvents.REDSTONE_TORCH_BURNOUT,
						SoundSource.BLOCKS,
						0.5F,
						pitch
				);
			}

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		list.add(new TranslatableComponent("item.ftbic.spray_paint_can.tooltip").withStyle(ChatFormatting.GRAY));
	}
}
