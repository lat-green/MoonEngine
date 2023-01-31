package com.greentree.engine.moon.render.pipeline;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.render.material.Material;
import com.greentree.engine.moon.render.pipeline.buffer.CommandBuffer;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextute;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;

public interface RenderContext {
	
	RenderTargetTextuteBuilder createRenderTarget();
	
	Material getDefaultShadowMaterial();
	
	Material getDefaultCubeMapShadowMaterial();
	
	RenderTargetTextute getTarget(Entity camera);
	
	Material getSkyBox(Entity camera);
	
	CommandBuffer buffer();
	
	void swapBuffer();
	
}
