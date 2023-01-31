package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.commons.image.PixelFormat;

public interface RenderTargetTextuteBuilder {
	
	RenderTargetTextuteBuilder addColor2D(PixelFormat format);
	
	default RenderTargetTextuteBuilder addColor2D() {
		return addColor2D(PixelFormat.RGB);
	}
	
	RenderTargetTextuteBuilder addColorCubeMap(PixelFormat format);
	
	default RenderTargetTextuteBuilder addColorCubeMap() {
		return addColor2D(PixelFormat.RGB);
	}
	
	RenderTargetTextuteBuilder addDepth();
	RenderTargetTextuteBuilder addDepthCubeMapTexture();
	RenderTargetTextuteBuilder addDepthTexture();
	
	RenderTargetTextute build(int width, int height);
	
}
