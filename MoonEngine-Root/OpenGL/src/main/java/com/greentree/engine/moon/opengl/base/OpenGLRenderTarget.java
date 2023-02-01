package com.greentree.engine.moon.opengl.base;

import com.greentree.engine.moon.opengl.command.OpenGLContext;
import com.greentree.engine.moon.render.pipeline.target.RenderTarget;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;


public record OpenGLRenderTarget(OpenGLContext context) implements RenderTarget {
	
	@Override
	public TargetCommandBuffer buffer() {
		return new OpenGLCommandBuffer(context);
	}
	
}
