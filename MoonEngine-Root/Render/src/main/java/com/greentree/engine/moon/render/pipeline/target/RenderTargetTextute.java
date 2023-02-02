package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.render.texture.Texture;

public interface RenderTargetTextute extends RenderTarget, AutoCloseable {
	
	@Override
	void close();
	
	default Texture getColorTexture() {
		return getColorTexture(0);
	}
	
	Texture getColorTexture(int index);
	
	Texture getDepthTexture();
	
}
