package com.greentree.engine.moon.render.pipeline;

import com.greentree.commons.image.image.ImageData;
import com.greentree.engine.moon.render.material.Material;
import com.greentree.engine.moon.render.material.MaterialProperty;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;
import com.greentree.engine.moon.render.shader.ShaderProgram;
import com.greentree.engine.moon.render.shader.data.ShaderProgramData;
import com.greentree.engine.moon.render.texture.CubeImageData;

public interface RenderLibraryContext {
	
	RenderTargetTextuteBuilder createRenderTarget();
	
	TargetCommandBuffer buffer();
	
	void swapBuffer();
	
	ShaderProgram build(ShaderProgramData programData);
	
	MaterialProperty build(ImageData image);
	
	MaterialProperty build(CubeImageData image);
	
	Material getDefaultShadowMaterial();
	
	Material getDefaultCubeMapShadowMaterial();
	
	Material getDefaultSpriteMaterial();
	
}
