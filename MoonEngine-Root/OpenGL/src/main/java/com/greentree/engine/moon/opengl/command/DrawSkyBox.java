package com.greentree.engine.moon.opengl.command;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.common.renderer.material.Material;
import com.greentree.common.renderer.opengl.material.OpenGLPropertyContext;

public record DrawSkyBox(Material material) implements OpenGLCommand {
	
	@Override
	public void run(OpenGLContext context) {
		glEnable(GL_DEPTH_TEST);
		
		final var p = (GLShaderProgram) material.shader();
		p.bind();
		
		material.set(new OpenGLPropertyContext(1));
		glActiveTexture(GL_TEXTURE0);
		
		
		glDepthFunc(GL_LEQUAL);
		context.renderBox();
		glDepthFunc(GL_LESS);
		
		p.unbind();
		
		glDisable(GL_DEPTH_TEST);
	}
	
}
