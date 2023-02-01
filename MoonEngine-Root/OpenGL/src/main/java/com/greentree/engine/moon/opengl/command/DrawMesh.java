package com.greentree.engine.moon.opengl.command;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import com.greentree.common.graphics.sgl.enums.gl.GLPrimitive;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.material.Material;

public record DrawMesh(StaticMesh mesh, Material material) implements OpenGLCommand {
	
	@Override
	public void run(OpenGLContext context) {
		final var vao = context.getVAO(mesh);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		
		try(final var p = material.buffer();) {
			vao.bind();
			glDrawArrays(GLPrimitive.TRIANGLES.glEnum, 0, vao.size());
			vao.unbind();
		}
		
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		glActiveTexture(GL_TEXTURE0);
	}
	
}
