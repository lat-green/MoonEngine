package com.greentree.engine.moon.opengl.adapter;

import static org.lwjgl.opengl.GL11.*;

import com.greentree.common.graphics.sgl.vao.GLVertexArray;


public record VAORenderMeshAdapter(GLVertexArray vao) implements RenderMesh {
	
	@Override
	public void render() {
		glDrawArrays(GL_TRIANGLES, 0, vao.size());
	}
	
	@Override
	public void bind() {
		vao.bind();
		
	}
	
	@Override
	public void unbind() {
		vao.unbind();
	}
	
}
