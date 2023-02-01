package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.render.material.MaterialProperty;

public interface RenderTargetTextute extends RenderTarget, AutoCloseable {
	
	@Override
	void close();
	
	default MaterialProperty getColorTexture() {
		return getColorTexture(0);
	}
	
	MaterialProperty getColorTexture(int index);
	
	MaterialProperty getDepthTexture();
	
}
