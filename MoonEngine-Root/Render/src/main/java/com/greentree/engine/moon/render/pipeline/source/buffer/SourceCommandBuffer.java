package com.greentree.engine.moon.render.pipeline.source.buffer;

import com.greentree.engine.moon.mesh.StaticMesh;

public interface SourceCommandBuffer extends AutoCloseable {
	
	@Override
	void close();
	void drawMesh(StaticMesh mesh);
	
}
