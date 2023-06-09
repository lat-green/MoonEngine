package com.greentree.engine.moon.render.camera;

import com.greentree.engine.moon.ecs.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.render.pipeline.target.CameraRenderTarget;

@RequiredComponent({CameraComponent.class})
public record CameraTarget(CameraRenderTarget target) implements ConstComponent {

}
