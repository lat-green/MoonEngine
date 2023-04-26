package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.render.material.Property;
import com.greentree.engine.moon.render.pipeline.target.buffer.CameraCommandBuffer;

public record CameraRenderTarget(Entity camera, RenderTargetTextute target) implements RenderTargetTextute {
	
	@Override
	public String toString() {
		return "CameraRenderTarget [" + target + "]";
	}
	
	@Override
	public CameraCommandBuffer buffer() {
		return new CameraCommandBuffer(camera, target.buffer());
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
