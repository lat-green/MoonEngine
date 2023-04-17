package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.render.material.Property;

public interface RenderTargetTextute extends RenderTarget {
	
	default Property getColorTexture() {
		return getColorTexture(0);
	}
	
	Property getColorTexture(int index);
	
	Property getDepthTexture();
	
}
