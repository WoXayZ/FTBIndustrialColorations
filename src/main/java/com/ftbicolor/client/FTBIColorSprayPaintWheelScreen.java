package com.ftbicolor.client;

import com.ftbicolor.item.FTBIColorSprayPaintKitType;
import com.ftbicolor.network.FTBIColorSetSprayPaintKitColorPacket;
import com.ftbicolor.platform.FTBIColorText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class FTBIColorSprayPaintWheelScreen extends Screen {
	private static final int INNER_RADIUS = 36;
	private static final int OUTER_RADIUS = 110;

	private final int source;
	private final FTBIColorSprayPaintKitType kitType;
	private final List<DyeColor> colors;
	private final boolean holdMode;

	private int hoveredIndex = -1;
	private boolean keyConfirmHandled;

	public FTBIColorSprayPaintWheelScreen(Player player, int source, FTBIColorSprayPaintKitType kitType, boolean holdMode) {
		super(FTBIColorText.empty());
		this.source = source;
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
			Minecraft.getInstance().setScreen(null);
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
		hoveredIndex = segmentAt(mouseX, mouseY);
		int centerX = width / 2;
		int centerY = height / 2;
		float segmentAngle = (float) (Math.PI * 2.0 / colors.size());
		float startAngle = (float) (-Math.PI / 2.0 - segmentAngle / 2.0);
		for (int i = 0; i < colors.size(); i++) {
			float a0 = startAngle + segmentAngle * i;
			float a1 = a0 + segmentAngle;
			int rgb = colors.get(i).getTextureDiffuseColor() & 0xFFFFFF;
			int alpha = i == hoveredIndex ? 220 : 160;
			fillSegment(graphics, centerX, centerY, a0, a1, (alpha << 24) | rgb);
			if (i == hoveredIndex) {
				fillSegment(graphics, centerX, centerY, a0, a1, 0x55FFFFFF);
			}
		}

		Component label = hoveredIndex >= 0
				? FTBIColorText.translatable("color.minecraft." + colors.get(hoveredIndex).getName())
				: FTBIColorText.translatable("screen.ftbicolor.spray_paint_wheel.title");
		int textWidth = font.width(label);
		graphics.text(font, label, centerX - textWidth / 2, centerY - 4, hoveredIndex >= 0 ? 0xFFFFFFFF : 0xFFAAAAAA, true);
		super.extractRenderState(graphics, mouseX, mouseY, partialTick);
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
		if (event.button() == 0) {
			int index = segmentAt((int) event.x(), (int) event.y());
			if (index >= 0) {
				selectColor(colors.get(index));
				return true;
			}

			if (isCenter(event.x(), event.y())) {
				FTBIColorSetSprayPaintKitColorPacket.sendDeselect(source, kitType);
				Minecraft.getInstance().setScreen(null);
				return true;
			}
		}

		return super.mouseClicked(event, doubleClick);
	}

	@Override
	public boolean keyReleased(KeyEvent event) {
		if (holdMode && FTBIColorKeyBindings.SPRAY_PAINT_WHEEL.matches(event)) {
			confirmOrCloseFromKey();
			return true;
		}

		return super.keyReleased(event);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		if (holdMode && FTBIColorKeyBindings.SPRAY_PAINT_WHEEL.matches(event)) {
			return true;
		}

		if (event.key() == GLFW.GLFW_KEY_ESCAPE) {
			Minecraft.getInstance().setScreen(null);
			return true;
		}

		return super.keyPressed(event);
	}

	private void selectColor(DyeColor color) {
		FTBIColorSetSprayPaintKitColorPacket.sendSelect(source, kitType, color);
		Minecraft.getInstance().setScreen(null);
	}

	private boolean isCenter(double mouseX, double mouseY) {
		double dx = mouseX - width / 2.0;
		double dy = mouseY - height / 2.0;
		return dx * dx + dy * dy < (double) INNER_RADIUS * INNER_RADIUS;
	}

	private void fillSegment(GuiGraphicsExtractor graphics, int centerX, int centerY, float startAngle, float endAngle, int color) {
		graphics.submitGuiElementRenderState(new FTBIColorWheelSegment(
				graphics.pose(),
				graphics.peekScissorStack(),
				centerX,
				centerY,
				INNER_RADIUS,
				OUTER_RADIUS,
				startAngle,
				endAngle,
				color
		));
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
}
