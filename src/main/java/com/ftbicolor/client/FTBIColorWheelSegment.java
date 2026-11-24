package com.ftbicolor.client;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fc;

public final class FTBIColorWheelSegment implements GuiElementRenderState {
	private static final int SLICES = 24;
	private static final RenderPipeline PIPELINE = RenderPipelines.GUI.toBuilder()
			.withLocation(Identifier.fromNamespaceAndPath("ftbicolor", "pipeline/wheel"))
			.withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLE_STRIP)
			.build();

	private final Matrix3x2fc pose;
	private final ScreenRectangle scissorArea;
	private final ScreenRectangle bounds;
	private final int centerX;
	private final int centerY;
	private final int innerRadius;
	private final int outerRadius;
	private final float startAngle;
	private final float endAngle;
	private final int color;

	public FTBIColorWheelSegment(
			Matrix3x2fc pose,
			ScreenRectangle scissorArea,
			int centerX,
			int centerY,
			int innerRadius,
			int outerRadius,
			float startAngle,
			float endAngle,
			int color
	) {
		this.pose = new Matrix3x2f(pose);
		this.scissorArea = scissorArea;
		this.centerX = centerX;
		this.centerY = centerY;
		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;
		this.startAngle = startAngle;
		this.endAngle = endAngle;
		this.color = color;
		this.bounds = bounds(centerX, centerY, outerRadius, startAngle, endAngle, this.pose, scissorArea);
	}

	@Override
	public void buildVertices(VertexConsumer consumer) {
		float step = (endAngle - startAngle) / SLICES;
		for (int i = 0; i <= SLICES; i++) {
			float angle = startAngle + step * i;
			vertex(consumer, outerRadius, angle);
			vertex(consumer, innerRadius, angle);
		}
	}

	@Override
	public RenderPipeline pipeline() {
		return PIPELINE;
	}

	@Override
	public TextureSetup textureSetup() {
		return TextureSetup.noTexture();
	}

	@Override
	public ScreenRectangle scissorArea() {
		return scissorArea;
	}

	@Override
	public ScreenRectangle bounds() {
		return bounds;
	}

	private void vertex(VertexConsumer consumer, int radius, float angle) {
		consumer.addVertexWith2DPose(pose, centerX + Mth.cos(angle) * radius, centerY + Mth.sin(angle) * radius).setColor(color);
	}

	private static ScreenRectangle bounds(
			int centerX,
			int centerY,
			int outerRadius,
			float startAngle,
			float endAngle,
			Matrix3x2fc pose,
			ScreenRectangle scissorArea
	) {
		float minX = Float.POSITIVE_INFINITY;
		float minY = Float.POSITIVE_INFINITY;
		float maxX = Float.NEGATIVE_INFINITY;
		float maxY = Float.NEGATIVE_INFINITY;
		float step = (endAngle - startAngle) / SLICES;
		for (int i = 0; i <= SLICES; i++) {
			float angle = startAngle + step * i;
			float x = centerX + Mth.cos(angle) * outerRadius;
			float y = centerY + Mth.sin(angle) * outerRadius;
			minX = Math.min(minX, x);
			minY = Math.min(minY, y);
			maxX = Math.max(maxX, x);
			maxY = Math.max(maxY, y);
		}

		ScreenRectangle rectangle = new ScreenRectangle(
				Mth.floor(minX),
				Mth.floor(minY),
				Mth.ceil(maxX - minX) + 1,
				Mth.ceil(maxY - minY) + 1
		).transformMaxBounds(pose);
		return scissorArea == null ? rectangle : scissorArea.intersection(rectangle);
	}
}
