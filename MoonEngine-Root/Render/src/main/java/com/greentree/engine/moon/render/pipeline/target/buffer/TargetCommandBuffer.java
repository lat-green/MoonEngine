package com.greentree.engine.moon.render.pipeline.target.buffer;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.AttributeData;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.render.material.MaterialProperties;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

public interface TargetCommandBuffer extends AutoCloseable {
	
	
	StaticMeshFaceComponent[] COMPONENTS = {StaticMeshFaceComponent.VERTEX, StaticMeshFaceComponent.NORMAL,
			StaticMeshFaceComponent.TEXTURE_COORDINAT, StaticMeshFaceComponent.TANGENT};
	
	void clear();
	
	default void clear(Color color, float depth) {
		clearColor(color);
		clearDepth(depth);
	}
	
	void clearColor(Color color);
	
	void clearDepth(float depth);
	
	
	@Deprecated
	@Override
	default void close() {
		execute();
		clear();
	}
	
	void disableCullFace();
	void disableDepthTest();
	
	default void drawMesh(StaticMesh mesh, ShaderProgramData shader, MaterialProperties properties) {
		drawMesh(
				mesh.getAttributeGroup(COMPONENTS),
				shader, properties);
	}
	void drawMesh(AttributeData mesh, ShaderProgramData shader, MaterialProperties properties);
	
	
	void enableCullFace();
	
	void enableDepthTest();
	void execute();
	
	
}
