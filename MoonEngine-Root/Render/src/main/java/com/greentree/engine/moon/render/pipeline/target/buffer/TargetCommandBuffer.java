package com.greentree.engine.moon.render.pipeline.target.buffer;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.mesh.MeshUtil;
import com.greentree.engine.moon.render.pipeline.material.Material;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;

public interface TargetCommandBuffer extends AutoCloseable {
	
	default void clearRenderTarget(Color color, float depth) {
		clearRenderTargetColor(color);
		clearRenderTargetDepth(depth);
	}
	
	void clearRenderTargetColor(Color color);
	
	default void clearRenderTargetDepth() {
		clearRenderTargetDepth(1);
	}
	
	void clearRenderTargetDepth(float depth);
	
	@Override
	void close();
	
	void drawMesh(StaticMesh mesh, Shader material, MaterialProperties properties);
	
	default void drawMesh(StaticMesh mesh, Matrix4f model, Shader material,
			MaterialProperties properties) {
		properties.put("model", model);
		drawMesh(mesh, material, properties);
	}
	
	default void drawMesh(StaticMesh mesh, Matrix4f model, Material material) {
		drawMesh(mesh, model, material.shader(), material.properties());
	}
	
	default void drawMeshInstanced(StaticMesh mesh, Iterable<Matrix4f> model, Shader material,
			MaterialProperties properties) {
		for(var m : model) {
			properties.put("model", m);
			drawMesh(mesh, material, properties);
		}
	}
	
	default void drawSkyBox(Material material) {
		drawSkyBox(material.shader(), material.properties());
	}
	
	void drawSkyBox(Shader material, MaterialProperties properties);
	
	default void drawTexture(Shader material, MaterialProperties properties) {
		drawMesh(MeshUtil.QUAD, material, properties);
	}
	
}
