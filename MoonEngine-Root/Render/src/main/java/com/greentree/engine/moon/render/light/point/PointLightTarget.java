package com.greentree.engine.moon.render.light.point;

import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextute;

@RequiredComponent(PointLightComponent.class)
public record PointLightTarget(RenderTargetTextute target) implements ConstComponent {
}
