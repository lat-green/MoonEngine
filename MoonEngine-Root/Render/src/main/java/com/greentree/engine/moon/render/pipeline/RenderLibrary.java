package com.greentree.engine.moon.render.pipeline;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.render.mesh.RenderMesh;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.target.RenderTarget;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;
import com.greentree.engine.moon.render.shader.ShaderProgramData;
import com.greentree.engine.moon.render.texture.Texture;
import com.greentree.engine.moon.render.texture.data.CubeTextureData;
import com.greentree.engine.moon.render.texture.data.Texture2DData;

public interface RenderLibrary {
	
	StaticMeshFaceComponent[] COMPONENTS = {StaticMeshFaceComponent.VERTEX,
			StaticMeshFaceComponent.NORMAL,StaticMeshFaceComponent.TEXTURE_COORDINAT,
			StaticMeshFaceComponent.TANGENT};
	
	default void clearRenderTarget(Color color, float depth) {
		clearRenderTargetColor(color);
		clearRenderTargetDepth(depth);
	}
	
	void clearRenderTargetColor(Color color);
	
	default void clearRenderTargetDepth() {
		clearRenderTargetDepth(1);
	}
	
	void clearRenderTargetDepth(float depth);
	
	
	RenderTargetTextuteBuilder createRenderTarget();
	
	RenderTarget screanRenderTarget();
	
	default RenderMesh build(StaticMesh mesh) {
		return build(mesh, COMPONENTS);
	}
	
	RenderMesh build(StaticMesh mesh, StaticMeshFaceComponent... components);
	Texture build(CubeTextureData texture);
	Texture build(Texture2DData texture);
	Shader build(ShaderProgramData program);
	
}
