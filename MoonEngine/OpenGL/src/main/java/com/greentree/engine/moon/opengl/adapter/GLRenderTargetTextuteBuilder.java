package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.freambuffer.FreamBufferBuilder;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture;
import com.greentree.commons.graphics.smart.target.FrameBuffer;
import com.greentree.commons.graphics.smart.target.RenderTarget;
import com.greentree.commons.image.PixelFormat;

import java.util.Objects;

public class GLRenderTargetTextuteBuilder implements FrameBuffer.Builder {

	private final FreamBufferBuilder builder = new FreamBufferBuilder();

	private final RenderTarget context;

	public GLRenderTargetTextuteBuilder(RenderTarget target) {
		Objects.requireNonNull(target);
		this.context = target;
	}

	@Override
	public GLRenderTargetTextuteBuilder addColor2D(PixelFormat format) {
		builder.addColor2D(GLPixelFormat.gl(format));
		return this;
	}

	@Override
	public GLRenderTargetTextuteBuilder addDepth() {
		builder.addDepth();
		return this;
	}

	@Override
	public GLRenderTargetTextuteBuilder addDepthCubeMapTexture() {
		builder.addDepth(new GLCubeMapTexture());
		return this;
	}

	@Override
	public GLRenderTargetTextuteBuilder addDepthTexture() {
		builder.addDepth(new GLTexture2DImpl());
		return this;
	}

	@Override
	public FreamBufferRenderTarget build(int width, int height) {
		return new FreamBufferRenderTarget(builder.build(width, height), context);
	}

	@Override
	public GLRenderTargetTextuteBuilder addColorCubeMap(PixelFormat format) {
		builder.addColor(new GLCubeMapTexture(), GLPixelFormat.gl(format));
		return this;
	}

}
