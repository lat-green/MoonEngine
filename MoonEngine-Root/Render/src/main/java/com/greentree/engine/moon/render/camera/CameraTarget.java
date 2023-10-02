package com.greentree.engine.moon.render.camera;

import com.greentree.commons.graphics.smart.target.FrameBuffer;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({CameraComponent.class})
public record CameraTarget(FrameBuffer target) implements ConstComponent {

}
