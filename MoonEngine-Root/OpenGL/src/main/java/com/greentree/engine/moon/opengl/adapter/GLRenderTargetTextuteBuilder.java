package com.greentree.engine.moon.opengl.adapter;

import java.util.Objects;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.freambuffer.FreamBufferBuilder;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture;
import com.greentree.commons.image.PixelFormat;
import com.greentree.engine.moon.render.pipeline.target.RenderTarget;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextute;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;


public class GLRenderTargetTextuteBuilder implements RenderTargetTextuteBuilder {
	
	private final FreamBufferBuilder builder = new FreamBufferBuilder();
	
	private final RenderTarget context;
	
	public GLRenderTargetTextuteBuilder(RenderTarget target) {
		Objects.requireNonNull(target);
		this.context = target;
	}
	
	
	@Override
	public RenderTargetTextuteBuilder addColor2D(PixelFormat format) {
		builder.addColor2D(GLPixelFormat.gl(format));
		return this;
	}
	
	@Override
	public RenderTargetTextuteBuilder addDepth() {
		builder.addDepth();
		return this;
	}
	
	@Override
	public RenderTargetTextuteBuilder addDepthCubeMapTexture() {
		builder.addDepth(new GLCubeMapTexture());
		return this;
	}
	
	@Override
	public RenderTargetTextuteBuilder addDepthTexture() {
		builder.addDepth(new GLTexture2DImpl());
		return this;
	}
	
	@Override
	public RenderTargetTextute build(int width, int height) {
		return new FreamBufferRenderTarget(builder.build(width, height), context);
	}
	
	
	@Override
	public RenderTargetTextuteBuilder addColorCubeMap(PixelFormat format) {
		builder.addColor(new GLCubeMapTexture(), GLPixelFormat.gl(format));
		return this;
	}
	
}
