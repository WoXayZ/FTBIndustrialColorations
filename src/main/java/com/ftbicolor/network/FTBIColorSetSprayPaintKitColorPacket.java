package com.ftbicolor.network;

import com.ftbicolor.FTBIColorConstants;
import com.ftbicolor.item.FTBIColorKitSource;
import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import com.ftbicolor.item.FTBIColorSprayPaintKitType;
import com.ftbicolor.item.FTBIColorSprayPaintKitUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.Nullable;

public record FTBIColorSetSprayPaintKitColorPacket(int source, FTBIColorSprayPaintKitType kitType, int colorId) implements CustomPacketPayload {
	public static final Type<FTBIColorSetSprayPaintKitColorPacket> TYPE =
			new Type<>(Identifier.fromNamespaceAndPath(FTBIColorConstants.MOD_ID, "set_spray_paint_kit_color"));

	public static final StreamCodec<FriendlyByteBuf, FTBIColorSetSprayPaintKitColorPacket> STREAM_CODEC = StreamCodec.of(
			(buf, packet) -> {
				buf.writeByte(packet.source);
				buf.writeEnum(packet.kitType);
				buf.writeVarInt(packet.colorId);
			},
			buf -> new FTBIColorSetSprayPaintKitColorPacket(buf.readByte(), buf.readEnum(FTBIColorSprayPaintKitType.class), buf.readVarInt())
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

	public static void handle(FTBIColorSetSprayPaintKitColorPacket packet, IPayloadContext context) {
		context.enqueueWork(() -> {
			Player player = context.player();
			if (player instanceof ServerPlayer serverPlayer) {
				packet.apply(serverPlayer);
			}
		});
	}

	public void apply(ServerPlayer player) {
		ItemStack stack = FTBIColorKitSource.stack(player, source);
		if (!(stack.getItem() instanceof FTBIColorSprayPaintKitItem kit) || kit.kitType != kitType) {
			return;
		}

		DyeColor color = colorId < 0 ? null : DyeColor.byId(colorId);
		FTBIColorSprayPaintKitUtil.applyColor(player, stack, kitType, color);
		FTBIColorKitSource.save(player, source, stack);
	}

	public static void send(int source, FTBIColorSprayPaintKitType kitType, @Nullable DyeColor color) {
		int colorId = color == null ? -1 : color.getId();
		FTBIColorNetwork.sendToServer(new FTBIColorSetSprayPaintKitColorPacket(source, kitType, colorId));
	}

	public static void sendDeselect(int source, FTBIColorSprayPaintKitType kitType) {
		send(source, kitType, null);
	}

	public static void sendSelect(int source, FTBIColorSprayPaintKitType kitType, DyeColor color) {
		send(source, kitType, color);
	}
}
