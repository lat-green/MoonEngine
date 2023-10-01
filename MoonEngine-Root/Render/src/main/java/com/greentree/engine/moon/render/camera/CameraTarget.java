package com.greentree.engine.moon.render.camera;

import com.greentree.commons.graphics.smart.scene.CameraFrameBuffer;
import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({CameraComponent.class})
public record CameraTarget(CameraFrameBuffer target) implements ConstComponent {

}
