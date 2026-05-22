package com.ftbicolor.network;

import com.ftbicolor.item.FTBIColorSprayPaintKitItem;
import com.ftbicolor.item.FTBIColorSprayPaintKitType;
import com.ftbicolor.item.FTBIColorSprayPaintKitUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public final class FTBIColorSetSprayPaintKitColorPacket {
	private final InteractionHand hand;
	private final FTBIColorSprayPaintKitType kitType;
	private final int colorId;

	public FTBIColorSetSprayPaintKitColorPacket(InteractionHand hand, FTBIColorSprayPaintKitType kitType, int colorId) {
		this.hand = hand;
		this.kitType = kitType;
		this.colorId = colorId;
	}

	public static void encode(FTBIColorSetSprayPaintKitColorPacket packet, FriendlyByteBuf buf) {
		buf.writeEnum(packet.hand);
		buf.writeEnum(packet.kitType);
		buf.writeVarInt(packet.colorId);
	}

	public static FTBIColorSetSprayPaintKitColorPacket decode(FriendlyByteBuf buf) {
		InteractionHand hand = buf.readEnum(InteractionHand.class);
		FTBIColorSprayPaintKitType kitType = buf.readEnum(FTBIColorSprayPaintKitType.class);
		int colorId = buf.readVarInt();
		return new FTBIColorSetSprayPaintKitColorPacket(hand, kitType, colorId);
	}

	public static void handle(FTBIColorSetSprayPaintKitColorPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			if (player == null) {
				return;
			}

			ItemStack stack = player.getItemInHand(packet.hand);
			if (!(stack.getItem() instanceof FTBIColorSprayPaintKitItem kit) || kit.kitType != packet.kitType) {
				return;
			}

			@Nullable DyeColor color = packet.colorId < 0 ? null : DyeColor.byId(packet.colorId);
			FTBIColorSprayPaintKitUtil.applyColor(player, stack, packet.kitType, color);
			player.setItemInHand(packet.hand, stack);
		});
		context.setPacketHandled(true);
	}

	public static void send(InteractionHand hand, FTBIColorSprayPaintKitType kitType, @Nullable DyeColor color) {
		int colorId = color == null ? -1 : color.getId();
		FTBIColorNetwork.CHANNEL.sendToServer(new FTBIColorSetSprayPaintKitColorPacket(hand, kitType, colorId));
	}

	public static void sendDeselect(InteractionHand hand, FTBIColorSprayPaintKitType kitType) {
		send(hand, kitType, null);
	}

	public static void sendSelect(InteractionHand hand, FTBIColorSprayPaintKitType kitType, DyeColor color) {
		send(hand, kitType, color);
	}
}
