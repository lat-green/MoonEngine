package com.greentree.engine.moon.render.light.direction;

import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextute;

@RequiredComponent({DirectionLightComponent.class})
public record DirectionLightTarget(RenderTargetTextute target) implements ConstComponent {
	
	@Override
	public void close() {
		target.close();
	}
	
}
