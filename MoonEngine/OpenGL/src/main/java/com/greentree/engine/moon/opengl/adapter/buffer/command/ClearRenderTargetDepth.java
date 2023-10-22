package com.greentree.engine.moon.opengl.adapter.buffer.command;

import static org.lwjgl.opengl.GL11.*;

public record ClearRenderTargetDepth(float depth) implements TargetCommand {
	
	@Override
	public void run() {
		glClearDepth(depth);
		glClear(GL_DEPTH_BUFFER_BIT);
	}
	
}
