package com.greentree.engine.moon.opengl.base;

import com.greentree.common.renderer.opengl.command.OpenGLContext;
import com.greentree.common.renderer.pipeline.buffer.CommandBuffer;
import com.greentree.common.renderer.pipeline.target.RenderTarget;


public record OpenGLRenderTarget(OpenGLContext context) implements RenderTarget {
	
	@Override
	public CommandBuffer buffer() {
		return new OpenGLCommandBuffer(context);
	}
	
}
