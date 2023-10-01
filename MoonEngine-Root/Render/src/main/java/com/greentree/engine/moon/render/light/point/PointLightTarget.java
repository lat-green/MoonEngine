package com.greentree.engine.moon.render.light.point;

import com.greentree.commons.graphics.smart.target.FrameBuffer;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent(PointLightComponent.class)
public record PointLightTarget(FrameBuffer target) implements ConstComponent {
}
