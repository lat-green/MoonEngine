package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.render.pipeline.material.Property;
import com.greentree.engine.moon.render.pipeline.target.buffer.CameraCommandBuffer;

public record CameraRenderTarget(Entity camera, RenderTargetTextute target) implements RenderTargetTextute {
	
	@Override
	public CameraCommandBuffer buffer() {
		return new CameraCommandBuffer(camera, target.buffer());
	}
	
	@Override
	public void close() {
		target.close();
	}
	
	@Override
	public Property getColorTexture(int index) {
		return target.getColorTexture(index);
	}
	
	@Override
	public Property getDepthTexture() {
		return target.getDepthTexture();
	}
	
}
