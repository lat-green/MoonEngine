package com.greentree.engine.moon.render.camera;

import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.pipeline.target.CameraRenderTarget;

public record CameraTarget(CameraRenderTarget target) implements ConstComponent {
	
	@Override
	public void close() {
		target.close();
	}
	
}
