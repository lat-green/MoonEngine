package com.greentree.engine.moon.opengl.adapter;

import com.greentree.engine.moon.render.mesh.RenderMesh;

public interface RenderMeshAddapter extends RenderMesh {
	
	@Override
	default RenderMesh enableCullFace() {
		return new CullFaceRenderMeshAddapter(this);
	}
	
	@Override
	default RenderMesh enableDepthTest() {
		return new DepthTestRenderMeshAddapter(this);
	}
	
}
