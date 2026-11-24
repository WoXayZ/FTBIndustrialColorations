package com.ftbicolor.network;

import com.ftbicolor.item.FTBIColorKitSource;
import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import com.ftbicolor.item.FTBIColorSprayPaintKitType;
import com.ftbicolor.item.FTBIColorSprayPaintKitUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public final class FTBIColorSetSprayPaintKitColorPacket {
	private final int source;
	private final FTBIColorSprayPaintKitType kitType;
	private final int colorId;

	public FTBIColorSetSprayPaintKitColorPacket(int source, FTBIColorSprayPaintKitType kitType, int colorId) {
		this.source = source;
		this.kitType = kitType;
		this.colorId = colorId;
	}

	public static void encode(FTBIColorSetSprayPaintKitColorPacket packet, FriendlyByteBuf buf) {
		buf.writeByte(packet.source);
		buf.writeEnum(packet.kitType);
		buf.writeVarInt(packet.colorId);
	}

	public static FTBIColorSetSprayPaintKitColorPacket decode(FriendlyByteBuf buf) {
		int source = buf.readByte();
		FTBIColorSprayPaintKitType kitType = buf.readEnum(FTBIColorSprayPaintKitType.class);
		int colorId = buf.readVarInt();
		return new FTBIColorSetSprayPaintKitColorPacket(source, kitType, colorId);
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

	public static void handle(FTBIColorSetSprayPaintKitColorPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			if (player != null) {
				packet.apply(player);
			}
		});
		context.setPacketHandled(true);
	}

	public static void send(int source, FTBIColorSprayPaintKitType kitType, @Nullable DyeColor color) {
		int colorId = color == null ? -1 : color.getId();
		FTBIColorNetwork.CHANNEL.sendToServer(new FTBIColorSetSprayPaintKitColorPacket(source, kitType, colorId));
	}

	public static void sendDeselect(int source, FTBIColorSprayPaintKitType kitType) {
		send(source, kitType, null);
	}

	public static void sendSelect(int source, FTBIColorSprayPaintKitType kitType, DyeColor color) {
		send(source, kitType, color);
	}
}
