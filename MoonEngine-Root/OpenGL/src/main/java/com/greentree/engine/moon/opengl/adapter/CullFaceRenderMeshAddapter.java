package com.greentree.engine.moon.opengl.adapter;

import static org.lwjgl.opengl.GL11.*;

import com.greentree.engine.moon.render.mesh.RenderMesh;


public record CullFaceRenderMeshAddapter(RenderMesh mesh) implements RenderMeshAddapter {
	
	@Override
	public void render() {
		mesh.render();
	}
	
	@Override
	public RenderMesh enableDepthTest() {
		final var dt = mesh.enableDepthTest();
		if(dt == mesh)
			return this;
		return new CullFaceRenderMeshAddapter(dt);
	}
	
	@Override
	public RenderMesh enableCullFace() {
		return this;
	}
	
	@Override
	public void bind() {
		glEnable(GL_CULL_FACE);
		mesh.bind();
	}
	
	@Override
	public void unbind() {
		mesh.unbind();
		glDisable(GL_CULL_FACE);
	}
	
}
