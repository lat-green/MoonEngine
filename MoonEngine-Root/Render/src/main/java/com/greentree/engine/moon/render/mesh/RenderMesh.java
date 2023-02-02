package com.greentree.engine.moon.render.mesh;

public interface RenderMesh {
	
	void render();
	
	default RenderMesh enableCullFace() {
		throw new UnsupportedOperationException();
	}
	
	default RenderMesh enableDepthTest() {
		throw new UnsupportedOperationException();
	}
	
}
