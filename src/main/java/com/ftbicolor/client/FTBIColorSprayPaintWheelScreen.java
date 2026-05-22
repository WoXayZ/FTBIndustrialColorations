package com.ftbicolor.client;

import com.ftbicolor.item.FTBIColorSprayPaintKitType;
import com.ftbicolor.network.FTBIColorSetSprayPaintKitColorPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class FTBIColorSprayPaintWheelScreen extends Screen {
	private static final int INNER_RADIUS = 36;
	private static final int OUTER_RADIUS = 110;

	private final Player player;
	private final InteractionHand hand;
	private final FTBIColorSprayPaintKitType kitType;
	private final List<DyeColor> colors;
	private final boolean holdMode;

	private int hoveredIndex = -1;
	private boolean closingFromSelection;
	private boolean keyConfirmHandled;

	public FTBIColorSprayPaintWheelScreen(Player player, InteractionHand hand, FTBIColorSprayPaintKitType kitType, boolean holdMode) {
		super(net.minecraft.network.chat.TextComponent.EMPTY);
		this.player = player;
		this.hand = hand;
		this.kitType = kitType;
		this.colors = kitType.colors();
		this.holdMode = holdMode;
	}

	public boolean isHoldMode() {
		return holdMode;
	}

	public void confirmOrCloseFromKey() {
		if (keyConfirmHandled) {
			return;
		}

		keyConfirmHandled = true;
		if (hoveredIndex >= 0) {
			selectColor(colors.get(hoveredIndex));
		} else {
			closeWithDeselect();
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		renderBackground(poseStack);
		hoveredIndex = segmentAt(mouseX, mouseY);

		int centerX = width / 2;
		int centerY = height / 2;
		float segmentAngle = (float) (Math.PI * 2.0 / colors.size());
		float startAngle = (float) (-Math.PI / 2.0 - segmentAngle / 2.0);

		for (int i = 0; i < colors.size(); i++) {
			float a0 = startAngle + segmentAngle * i;
			float a1 = a0 + segmentAngle;
			int rgb = dyeRgb(colors.get(i));
			int alpha = i == hoveredIndex ? 220 : 160;
			int color = (alpha << 24) | (rgb & 0xFFFFFF);
			fillSegment(poseStack, centerX, centerY, INNER_RADIUS, OUTER_RADIUS, a0, a1, color);

			if (i == hoveredIndex) {
				fillSegment(poseStack, centerX, centerY, INNER_RADIUS, OUTER_RADIUS, a0, a1, 0x55FFFFFF);
			}
		}

		if (hoveredIndex >= 0) {
			Component label = new TranslatableComponent("color.minecraft." + colors.get(hoveredIndex).getName());
			drawCenteredString(poseStack, font, label, centerX, centerY - 4, 0xFFFFFF);
		} else {
			drawCenteredString(
					poseStack,
					font,
					new TranslatableComponent("screen.ftbicolor.spray_paint_wheel.title"),
					centerX,
					centerY - 4,
					0xAAAAAA
			);
		}

		super.render(poseStack, mouseX, mouseY, partialTick);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0 && hoveredIndex >= 0) {
			selectColor(colors.get(hoveredIndex));
			return true;
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		if (holdMode && FTBIColorKeyBindings.SPRAY_PAINT_WHEEL.matches(keyCode, scanCode)) {
			confirmOrCloseFromKey();
			return true;
		}

		return super.keyReleased(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (holdMode && FTBIColorKeyBindings.SPRAY_PAINT_WHEEL.matches(keyCode, scanCode)) {
			return true;
		}

		if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
			closeWithDeselect();
			return true;
		}

		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public void onClose() {
		if (!closingFromSelection) {
			FTBIColorSetSprayPaintKitColorPacket.sendDeselect(hand, kitType);
		}

		super.onClose();
	}

	private void selectColor(DyeColor color) {
		closingFromSelection = true;
		FTBIColorSetSprayPaintKitColorPacket.sendSelect(hand, kitType, color);
		Minecraft.getInstance().setScreen(null);
	}

	private void closeWithDeselect() {
		Minecraft.getInstance().setScreen(null);
	}

	private int segmentAt(int mouseX, int mouseY) {
		int centerX = width / 2;
		int centerY = height / 2;
		double dx = mouseX - centerX;
		double dy = mouseY - centerY;
		double distance = Math.sqrt(dx * dx + dy * dy);

		if (distance < INNER_RADIUS || distance > OUTER_RADIUS) {
			return -1;
		}

		float segmentAngle = (float) (Math.PI * 2.0 / colors.size());
		float startAngle = (float) (-Math.PI / 2.0 - segmentAngle / 2.0);
		double relative = Math.atan2(dy, dx) - startAngle;

		while (relative < 0.0) {
			relative += Math.PI * 2.0;
		}
		while (relative >= Math.PI * 2.0) {
			relative -= Math.PI * 2.0;
		}

		int index = (int) (relative / segmentAngle);
		return Mth.clamp(index, 0, colors.size() - 1);
	}

	private static int dyeRgb(DyeColor color) {
		float[] channels = color.getTextureDiffuseColors();
		int r = Mth.clamp((int) (channels[0] * 255.0F), 0, 255);
		int g = Mth.clamp((int) (channels[1] * 255.0F), 0, 255);
		int b = Mth.clamp((int) (channels[2] * 255.0F), 0, 255);
		return (r << 16) | (g << 8) | b;
	}

	private static void fillSegment(
			PoseStack poseStack,
			int centerX,
			int centerY,
			int innerRadius,
			int outerRadius,
			float startAngle,
			float endAngle,
			int color
	) {
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		int segments = 24;
		float step = (endAngle - startAngle) / segments;
		PoseStack.Pose pose = poseStack.last();
		com.mojang.blaze3d.vertex.Tesselator tesselator = com.mojang.blaze3d.vertex.Tesselator.getInstance();
		com.mojang.blaze3d.vertex.BufferBuilder buffer = tesselator.getBuilder();
		buffer.begin(com.mojang.blaze3d.vertex.VertexFormat.Mode.TRIANGLE_STRIP, com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR);

		float a = ((color >> 24) & 0xFF) / 255.0F;
		float r = ((color >> 16) & 0xFF) / 255.0F;
		float g = ((color >> 8) & 0xFF) / 255.0F;
		float b = (color & 0xFF) / 255.0F;

		for (int i = 0; i <= segments; i++) {
			float angle = startAngle + step * i;
			float cos = Mth.cos(angle);
			float sin = Mth.sin(angle);
			buffer.vertex(pose.pose(), centerX + cos * outerRadius, centerY + sin * outerRadius, 0.0F).color(r, g, b, a).endVertex();
			buffer.vertex(pose.pose(), centerX + cos * innerRadius, centerY + sin * innerRadius, 0.0F).color(r, g, b, a).endVertex();
		}

		tesselator.end();
		RenderSystem.disableBlend();
	}
}
