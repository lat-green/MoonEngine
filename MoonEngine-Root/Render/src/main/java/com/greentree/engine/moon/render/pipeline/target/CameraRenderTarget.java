package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.render.pipeline.buffer.CameraCommandBuffer;
import com.greentree.engine.moon.render.pipeline.buffer.CommandBuffer;

public record CameraRenderTarget(Entity camera, RenderTarget target) implements RenderTarget {
	
	@Override
	public CommandBuffer buffer() {
		return new CameraCommandBuffer(camera, target.buffer());
	}
	
}
