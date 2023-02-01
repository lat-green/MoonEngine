package com.greentree.engine.moon.render.pipeline.target.buffer;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.MeshUtil;
import com.greentree.engine.moon.render.material.Material;
import com.greentree.engine.moon.render.material.MaterialProperty;
import com.greentree.engine.moon.render.pipeline.RenderLibraryContext;

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
	
	void drawMesh(StaticMesh mesh, Material material);
	
	default void drawMesh(StaticMesh mesh, Matrix4f model, Material material) {
		final var properties = material.properties();
		properties.put("model", model);
		drawMesh(mesh, material);
	}
	
	default void drawMeshInstanced(StaticMesh mesh, Iterable<Matrix4f> model, Material material) {
		final var properties = material.properties();
		for(var m : model) {
			properties.put("model", m);
			drawMesh(mesh, material);
		}
	}
	
	void drawSkyBox(Material material);
	
	default void drawTexture(Material material) {
		drawMesh(MeshUtil.QUAD, material);
	}
	default void drawTexture(RenderLibraryContext context, MaterialProperty texture) {
		final var material = context.getDefaultSpriteMaterial();
		material.properties().put("texture", texture);
		drawTexture(material);
	}
	
}
