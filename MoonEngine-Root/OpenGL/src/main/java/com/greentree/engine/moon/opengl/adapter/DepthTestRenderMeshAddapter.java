package com.greentree.engine.moon.opengl.adapter;

import static org.lwjgl.opengl.GL11.*;

import com.greentree.engine.moon.render.mesh.RenderMesh;

public record DepthTestRenderMeshAddapter(RenderMesh mesh) implements RenderMeshAddapter {
	
	@Override
	public void render() {
		glEnable(GL_DEPTH_TEST);
		mesh.render();
		glDisable(GL_DEPTH_TEST);
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
	
}
