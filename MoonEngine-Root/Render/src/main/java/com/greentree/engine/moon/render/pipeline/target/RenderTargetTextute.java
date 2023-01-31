package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.render.material.TextureProperty;

public interface RenderTargetTextute extends RenderTarget, AutoCloseable {
	
	@Override
	void close();
	
	default TextureProperty getColorTexture() {
		return getColorTexture(0);
	}
	
	TextureProperty getColorTexture(int index);
	
	TextureProperty getDepthTexture();
	
}
