package com.ftbicolor.platform;

import com.ftbicolor.FTBIColorConstants;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class FTBIColorComponents {
	public static final DeferredRegister.DataComponents REGISTRY =
			DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, FTBIColorConstants.MOD_ID);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ARMOR_COLOR =
			REGISTRY.registerComponentType("armor_color", builder -> builder
					.persistent(Codec.INT)
					.networkSynchronized(ByteBufCodecs.VAR_INT));

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SELECTED_COLOR =
			REGISTRY.registerComponentType("selected_color", builder -> builder
					.persistent(Codec.INT)
					.networkSynchronized(ByteBufCodecs.VAR_INT));

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> CURIO_ACTIVE =
			REGISTRY.registerComponentType("curio_active", builder -> builder
					.persistent(Codec.BOOL)
					.networkSynchronized(ByteBufCodecs.BOOL));

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> PICKED_COLOR =
			REGISTRY.registerComponentType("picked_color", builder -> builder
					.persistent(Codec.STRING)
					.networkSynchronized(ByteBufCodecs.STRING_UTF8));

	private FTBIColorComponents() {
	}
}
