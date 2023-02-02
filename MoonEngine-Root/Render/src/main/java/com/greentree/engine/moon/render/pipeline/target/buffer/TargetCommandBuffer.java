package com.greentree.engine.moon.render.pipeline.target.buffer;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.mesh.RenderMesh;
import com.greentree.engine.moon.render.mesh.RenderMeshUtil;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
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
	
	default void drawMesh(RenderMesh mesh, Material material) {
		drawMesh(mesh, material.shader(), material.properties());
	}
	
	void drawMesh(RenderMesh mesh, Shader material, MaterialProperties properties);
	
	default void drawMesh(RenderMesh mesh, Matrix4f model, Shader material,
			MaterialProperties properties) {
		properties.put("model", model);
		drawMesh(mesh, material, properties);
	}
	
	default void drawMesh(RenderLibrary library, StaticMesh mesh, Matrix4f model,
			Material material) {
		final var rmesh = library.build(mesh).enableDepthTest().enableCullFace();
		drawMesh(rmesh, model, material.shader(), material.properties());
	}
	
	default void drawMesh(RenderMesh mesh, Matrix4f model, Material material) {
		drawMesh(mesh, model, material.shader(), material.properties());
	}
	
	default void drawSkyBox(Material material) {
		drawSkyBox(material.shader(), material.properties());
	}
	
	void drawSkyBox(Shader material, MaterialProperties properties);
	
	default void drawTexture(RenderLibrary library, Shader material,
			MaterialProperties properties) {
		drawMesh(RenderMeshUtil.QUAD(library), material, properties);
	}
	
}
