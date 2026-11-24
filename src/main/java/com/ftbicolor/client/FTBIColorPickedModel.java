package com.ftbicolor.client;

import com.ftbicolor.FTBIColor;
import com.ftbicolor.block.FTBIColorBlockStates;
import com.ftbicolor.block.FTBIColorBlocks;
import dev.ftb.mods.ftbic.block.CableBlock;
import dev.ftb.mods.ftbic.block.FTBICBlocks;
import dev.ftb.mods.ftbic.block.SprayPaintable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public final class FTBIColorPickedModel implements BakedModel {
	private final BakedModel original;
	private final ItemOverrides overrides = new ItemOverrides() {
		@Override
		public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable net.minecraft.client.multiplayer.ClientLevel level, @Nullable LivingEntity entity, int seed) {
			DyeColor color = FTBIColorBlockStates.pickedColor(stack);
			if (color == null || !(stack.getItem() instanceof net.minecraft.world.item.BlockItem blockItem)) {
				return original;
			}
			BakedModel colored = coloredModel(blockItem.getBlock(), color);
			return colored == null ? original : colored;
		}
	};

	public FTBIColorPickedModel(BakedModel original) {
		this.original = original;
	}

	@Nullable
	private static BakedModel coloredModel(Block block, DyeColor color) {
		if (block instanceof CableBlock) {
			var id = ForgeRegistries.BLOCKS.getKey(block);
			if (id == null) {
				return null;
			}
			var modelId = new net.minecraft.resources.ResourceLocation("ftbic", "item/cable/" + color.getSerializedName() + "/" + id.getPath());
			var manager = Minecraft.getInstance().getModelManager();
			BakedModel model = manager.getModel(modelId);
			return model == manager.getMissingModel() ? null : model;
		}

		Block target = block;
		if (block == FTBICBlocks.MACHINE_BLOCK.get()) {
			target = FTBIColorBlocks.COLORED_MACHINE_BLOCK.get();
		} else if (block == FTBICBlocks.ADVANCED_MACHINE_BLOCK.get()) {
			target = FTBIColorBlocks.COLORED_ADVANCED_MACHINE_BLOCK.get();
		} else if (block == FTBICBlocks.REINFORCED_STONE.get()) {
			target = FTBIColorBlocks.COLORED_REINFORCED_STONE.get();
		} else if (block == FTBICBlocks.REINFORCED_GLASS.get()) {
			target = FTBIColorBlocks.COLORED_REINFORCED_GLASS.get();
		}

		BlockState state = target.defaultBlockState();
		if (state.hasProperty(FTBIColor.COLOR)) {
			state = state.setValue(FTBIColor.COLOR, color);
		}
		if (state.hasProperty(FTBIColor.PAINTED)) {
			state = state.setValue(FTBIColor.PAINTED, true);
		}
		if (state.hasProperty(SprayPaintable.DARK)) {
			state = state.setValue(SprayPaintable.DARK, color == DyeColor.BLACK);
		}
		for (Property<?> property : state.getProperties()) {
			if (property instanceof DirectionProperty facing && property.getName().equals("facing") && facing.getPossibleValues().contains(Direction.NORTH)) {
				state = state.setValue(facing, Direction.NORTH);
			} else if (property instanceof BooleanProperty active && property.getName().equals("active")) {
				state = state.setValue(active, false);
			}
		}
		return Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
	}

	@Override
	public java.util.List<net.minecraft.client.renderer.block.model.BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random random) {
		return original.getQuads(state, side, random);
	}

	@Override
	public boolean useAmbientOcclusion() {
		return original.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return original.isGui3d();
	}

	@Override
	public boolean usesBlockLight() {
		return original.usesBlockLight();
	}

	@Override
	public boolean isCustomRenderer() {
		return original.isCustomRenderer();
	}

	@Override
	public net.minecraft.client.renderer.texture.TextureAtlasSprite getParticleIcon() {
		return original.getParticleIcon();
	}

	@Override
	public net.minecraft.client.renderer.block.model.ItemTransforms getTransforms() {
		return original.getTransforms();
	}

	@Override
	public ItemOverrides getOverrides() {
		return overrides;
	}
}
