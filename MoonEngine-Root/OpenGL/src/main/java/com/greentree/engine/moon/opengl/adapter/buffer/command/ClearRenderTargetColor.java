package com.greentree.engine.moon.opengl.adapter.buffer.command;

import static org.lwjgl.opengl.GL11.*;

import com.greentree.commons.image.Color;
public record ClearRenderTargetColor(Color color) implements TargetCommand {
	
	@Override
	public void run() {
		glClearColor(color.r, color.g, color.b, color.a);
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
}
