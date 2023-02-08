package com.greentree.engine.moon.opengl.adapter;

import static org.lwjgl.opengl.GL11.*;

import com.greentree.engine.moon.render.mesh.RenderMesh;

public record DepthTestRenderMeshAddapter(RenderMesh mesh) implements RenderMeshAddapter {
	
	@Override
	public void render() {
		mesh.render();
	}
	
	@Override
	public RenderMesh enableCullFace() {
		final var cf = mesh.enableCullFace();
		if(cf == mesh)
			return this;
		return new DepthTestRenderMeshAddapter(cf);
	}
	
	@Override
	public RenderMesh enableDepthTest() {
		return this;
	}
	
	@Override
	public void bind() {
		glEnable(GL_DEPTH_TEST);
		mesh.bind();
	}
	
	@Override
	public void unbind() {
		mesh.unbind();
		glDisable(GL_DEPTH_TEST);
	}
	
}
