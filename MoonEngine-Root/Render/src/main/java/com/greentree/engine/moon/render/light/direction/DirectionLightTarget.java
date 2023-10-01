package com.greentree.engine.moon.render.light.direction;

import com.greentree.commons.graphics.smart.target.FrameBuffer;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({DirectionLightComponent.class})
public record DirectionLightTarget(FrameBuffer target) implements ConstComponent {

}
