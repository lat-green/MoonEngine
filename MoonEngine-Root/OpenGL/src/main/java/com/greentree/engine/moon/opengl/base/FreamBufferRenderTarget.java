package com.greentree.engine.moon.opengl.base;

import com.greentree.common.graphics.sgl.freambuffer.AttachmentFreamBuffer;
import com.greentree.common.graphics.sgl.freambuffer.FreamBuffer;
import com.greentree.common.renderer.material.TextureProperty;
import com.greentree.common.renderer.opengl.command.OpenGLContext;
import com.greentree.common.renderer.opengl.material.GLTextureProperty;
import com.greentree.common.renderer.pipeline.buffer.CommandBuffer;
import com.greentree.common.renderer.pipeline.target.RenderTarget;
import com.greentree.common.renderer.pipeline.target.RenderTargetTextute;


public record FreamBufferRenderTarget(FreamBuffer framebuffer, RenderTarget context) implements RenderTargetTextute {
	
	public FreamBufferRenderTarget(AttachmentFreamBuffer build, OpenGLContext context) {
		this(build, new OpenGLRenderTarget(context));
	}
	
	
	@Override
	public CommandBuffer buffer() {
		return new FreamBufferCommandBuffer(framebuffer, context.buffer());
	}
	
	
	@Override
	public void close() {
		framebuffer.close();
	}
	
	@Override
	public TextureProperty getColorTexture(int index) {
		return new GLTextureProperty(framebuffer.getColorTexture(index));
	}
	
	@Override
	public TextureProperty getDepthTexture() {
		return new GLTextureProperty(framebuffer.getDepthTexture());
	}
	
}
