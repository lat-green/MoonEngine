package com.greentree.engine.moon.render.pipeline;

import com.greentree.engine.moon.render.material.Property;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextuteBuilder;
import com.greentree.engine.moon.render.texture.CubeTextureData;
import com.greentree.engine.moon.render.texture.Texture2DData;

public interface RenderLibrary {
	
	RenderTargetTextuteBuilder createRenderTarget();
	
	Property build(CubeTextureData texture);
	Property build(Texture2DData texture);
}
