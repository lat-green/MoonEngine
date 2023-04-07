package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.render.pipeline.material.Property;

public interface RenderTargetTextute extends RenderTarget, AutoCloseable {
	
	@Override
	void close();
	
	default Property getColorTexture() {
		return getColorTexture(0);
	}
	
	Property getColorTexture(int index);
	
	Property getDepthTexture();
	
}
