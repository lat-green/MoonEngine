package com.greentree.engine.moon.render.pipeline.material;

import com.greentree.engine.moon.mesh.StaticMesh;

public interface ShaderCommandBuffer extends AutoCloseable {
	
	@Override
	void close();
	void drawMesh(StaticMesh mesh);
	
}
