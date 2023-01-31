package com.greentree.engine.moon.opengl.base;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.freambuffer.FreamBufferBuilder;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture;
import com.greentree.common.renderer.opengl.command.OpenGLContext;
import com.greentree.common.renderer.pipeline.target.RenderTargetTextute;
import com.greentree.common.renderer.pipeline.target.RenderTargetTextuteBuilder;
import com.greentree.commons.image.PixelFormat;


public class GLRenderTargetTextuteBuilder implements RenderTargetTextuteBuilder {
	
	private final FreamBufferBuilder builder = new FreamBufferBuilder();
	
	private final OpenGLContext context;
	
	public GLRenderTargetTextuteBuilder(OpenGLContext context) {
		this.context = context;
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
