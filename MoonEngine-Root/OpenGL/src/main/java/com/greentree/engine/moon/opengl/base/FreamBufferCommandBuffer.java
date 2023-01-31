package com.greentree.engine.moon.opengl.base;

import static org.lwjgl.opengl.GL11.*;

import java.util.Objects;

import com.greentree.common.graphics.sgl.freambuffer.FreamBuffer;
import com.greentree.common.renderer.opengl.command.OpenGLContext;
import com.greentree.common.renderer.pipeline.buffer.CommandBuffer;
import com.greentree.common.renderer.pipeline.buffer.ProxyCommandBuffer;


public class FreamBufferCommandBuffer extends ProxyCommandBuffer implements CommandBuffer {
	
	protected final FreamBuffer fb;
	
	public FreamBufferCommandBuffer(FreamBuffer fb, OpenGLContext context) {
		this(fb, new OpenGLCommandBuffer(context));
	}
	
	public FreamBufferCommandBuffer(FreamBuffer fb, CommandBuffer buffer) {
		super(buffer);
		Objects.requireNonNull(fb);
		this.fb = fb;
	}
	
	@Override
	public void close() {
		fb.bind();
		glViewport(0, 0, fb.getWidth(), fb.getHeight());
		super.close();
		fb.unbind();
	}
	
}
