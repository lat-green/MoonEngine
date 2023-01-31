package com.greentree.engine.moon.opengl.command;

import static org.lwjgl.opengl.GL11.*;

import com.greentree.commons.image.Color;

public record ClearRenderTargetColor(Color color) implements OpenGLCommand {
	
	@Override
	public void run(OpenGLContext context) {
		glClearColor(color.r, color.g, color.b, color.a);
		glClear(GL_COLOR_BUFFER_BIT);
		
	}
	
}
