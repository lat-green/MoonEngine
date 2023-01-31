package com.greentree.engine.moon.opengl.command;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import com.greentree.common.graphics.sgl.enums.gl.GLPrimitive;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.opengl.render.material.OpenGLPropertyContext;
import com.greentree.engine.moon.render.material.Material;

public record DrawMesh(StaticMesh mesh, Material material) implements OpenGLCommand {
	
	@Override
	public void run(OpenGLContext context) {
		final var vao = context.getVAO(mesh);
		
		final var p = (GLShaderProgram) material.shader();
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		
		p.bind();
		
		material.set(new OpenGLPropertyContext(1));
		
		vao.bind();
		glDrawArrays(GLPrimitive.TRIANGLES.glEnum, 0, vao.size());
		vao.unbind();
		p.unbind();
		
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		glActiveTexture(GL_TEXTURE0);
	}
	
}
