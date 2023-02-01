package com.greentree.engine.moon.render.pipeline.target;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.render.pipeline.target.buffer.CameraCommandBuffer;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;

public record CameraRenderTarget(Entity camera, RenderTarget target) implements RenderTarget {
	
	@Override
	public TargetCommandBuffer buffer() {
		return new CameraCommandBuffer(camera, target.buffer());
	}
	
}
