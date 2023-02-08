package com.greentree.engine.moon.render.pipeline.target.buffer;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.render.mesh.RenderMesh;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;

public interface TargetCommandBuffer extends AutoCloseable {
	
	default void clear(Color color, float depth) {
		clearColor(color);
		clearDepth(depth);
	}
	
	void clearColor(Color color);
	
	void clearDepth(float depth);
	
	@Override
	void close();
	
	void drawMesh(RenderMesh mesh, Shader shader, MaterialProperties properties);
	
}
