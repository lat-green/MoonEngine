package com.greentree.engine.moon.render.pipeline;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.Shader;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;
import com.greentree.engine.moon.render.shader.ShaderProgramData;
import com.greentree.engine.moon.render.texture.Texture;
import com.greentree.engine.moon.render.texture.data.CubeTextureData;
import com.greentree.engine.moon.render.texture.data.Texture2DData;

public interface RenderLibrary {
	
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
	
	TargetCommandBuffer buffer();
	
	void swapBuffer();
	
	RenderMesh build(StaticMesh program);
	Texture build(CubeTextureData texture);
	Texture build(Texture2DData texture);
	Shader build(ShaderProgramData program);
	
	MaterialProperties newMaterialProperties();
	
}
