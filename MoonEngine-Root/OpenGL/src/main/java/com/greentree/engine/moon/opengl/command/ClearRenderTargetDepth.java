package com.greentree.engine.moon.opengl.command;

import static org.lwjgl.opengl.GL11.*;

public record ClearRenderTargetDepth(float depth) implements OpenGLCommand {
	
	@Override
	public void run(OpenGLContext context) {
		glClearDepth(depth);
		glClear(GL_DEPTH_BUFFER_BIT);
	}
	
}
