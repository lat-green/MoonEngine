package com.greentree.engine.moon.render.camera;

import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextute;

public record CameraTarget(RenderTargetTextute target) implements ConstComponent {
	
	@Override
	public void close() {
		target.close();
	}
	
}
