package com.greentree.engine.moon.opengl.adapter;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import com.greentree.common.graphics.sgl.Window;
import com.greentree.engine.moon.render.pipeline.target.RenderTarget;
import com.greentree.engine.moon.render.window.CursorInputMode;

public record WindowAddapter(Window window, GLRenderLibrary render)
		implements com.greentree.engine.moon.render.window.Window {
	
	@Override
	public int getHeight() {
		return window.getHeight();
	}
	
	@Override
	public int getWidth() {
		return window.getWidth();
	}
	
	
	@Override
	public RenderTarget screanRenderTarget() {
		return render;
	}
	
	@Override
	public String getTitle() {
		return window.getTitle();
	}
	
	@Override
	public void swapBuffer() {
		window.swapBuffer();
		glViewport(0, 0, window.getWidth(), window.getHeight());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	}
	
	@Override
	public boolean isShouldClose() {
		return window.isShouldClose();
	}
	
	@Override
	public void setInputMode(CursorInputMode mode) {
		final var glfw = switch(mode) {
			case DISABLED -> GLFW_CURSOR_DISABLED;
			case HIDDEN -> GLFW_CURSOR_HIDDEN;
			case NORMAL -> GLFW_CURSOR_NORMAL;
		};
		glfwSetInputMode(window.glID, GLFW_CURSOR, glfw);
	}
	
	@Override
	public CursorInputMode getInputMode() {
		final var glfw = glfwGetInputMode(window.glID, GLFW_CURSOR);
		return switch(glfw) {
			case GLFW_CURSOR_DISABLED -> CursorInputMode.DISABLED;
			case GLFW_CURSOR_HIDDEN -> CursorInputMode.HIDDEN;
			case GLFW_CURSOR_NORMAL -> CursorInputMode.NORMAL;
			default -> throw new IllegalArgumentException("Unexpected value: " + glfw);
		};
	}
	
}
