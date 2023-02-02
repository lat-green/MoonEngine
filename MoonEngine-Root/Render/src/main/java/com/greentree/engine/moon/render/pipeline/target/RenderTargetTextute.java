package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.render.pipeline.material.MaterialProperty;

public interface RenderTargetTextute extends RenderTarget, AutoCloseable {
	
	@Override
	void close();
	
	default void getColorTexture(MaterialProperty property) {
		getColorTexture(property, 0);
	}
	
	void getColorTexture(MaterialProperty property, int index);
	
	void getDepthTexture(MaterialProperty property);
	
}
