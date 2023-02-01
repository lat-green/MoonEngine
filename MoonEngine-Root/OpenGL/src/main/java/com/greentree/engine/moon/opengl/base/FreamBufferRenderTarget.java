package com.greentree.engine.moon.opengl.base;

import com.greentree.common.graphics.sgl.freambuffer.AttachmentFreamBuffer;
import com.greentree.common.graphics.sgl.freambuffer.FreamBuffer;
import com.greentree.engine.moon.opengl.command.OpenGLContext;
import com.greentree.engine.moon.opengl.render.material.GLTextureProperty;
import com.greentree.engine.moon.render.material.MaterialProperty;
import com.greentree.engine.moon.render.pipeline.target.RenderTarget;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextute;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;


public record FreamBufferRenderTarget(FreamBuffer framebuffer, RenderTarget context)
		implements RenderTargetTextute {
	
	public FreamBufferRenderTarget(AttachmentFreamBuffer build, OpenGLContext context) {
		this(build, new OpenGLRenderTarget(context));
	}
	
	
	@Override
	public TargetCommandBuffer buffer() {
		return new FreamBufferCommandBuffer(framebuffer, context.buffer());
	}
	
	
	@Override
	public void close() {
		framebuffer.close();
	}
	
	@Override
	public MaterialProperty getColorTexture(int index) {
		return new GLTextureProperty(framebuffer.getColorTexture(index));
	}
	
	@Override
	public MaterialProperty getDepthTexture() {
		return new GLTextureProperty(framebuffer.getDepthTexture());
	}
	
}
