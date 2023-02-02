package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.render.pipeline.target.buffer.CameraCommandBuffer;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;
import com.greentree.engine.moon.render.texture.Texture;

public record CameraRenderTarget(Entity camera, RenderTargetTextute target)
		implements RenderTargetTextute {
	
	@Override
	public TargetCommandBuffer buffer() {
		return new CameraCommandBuffer(camera, target.buffer());
	}
	
	@Override
	public void close() {
		target.close();
	}
	
	@Override
	public Texture getColorTexture(int index) {
		return target.getColorTexture(index);
	}
	
	@Override
	public Texture getDepthTexture() {
		return target.getDepthTexture();
	}
	
}
