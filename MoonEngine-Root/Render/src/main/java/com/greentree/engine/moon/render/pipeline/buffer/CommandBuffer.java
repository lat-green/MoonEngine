package com.greentree.engine.moon.render.pipeline.buffer;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.material.Material;
import com.greentree.engine.moon.render.material.TextureProperty;

public interface CommandBuffer extends AutoCloseable {
	
	@Override
	void close();
	
	default void clearRenderTarget(Color color, float depth) {
		clearRenderTargetColor(color);
		clearRenderTargetDepth(depth);
	}
	
	void clearRenderTargetColor(Color color);
	void clearRenderTargetDepth(float depth);
	
	default void clearRenderTargetDepth() {
		clearRenderTargetDepth(1);
	}
	
	void drawMesh(StaticMesh mesh, Material material);
	
	default void drawMeshInstanced(StaticMesh mesh, Iterable<Matrix4f> model, Material material) {
		final var properties = material.properties();
		for(var m : model) {
			properties.put("model", m);
			drawMesh(mesh, material);
		}
	}
	
	default void drawMesh(StaticMesh mesh, Matrix4f model, Material material) {
		final var properties = material.properties();
		properties.put("model", model);
		drawMesh(mesh, material);
	}
	
	void drawSkyBox(Material material);
	
	void drawTexture(TextureProperty texture);
	
}
