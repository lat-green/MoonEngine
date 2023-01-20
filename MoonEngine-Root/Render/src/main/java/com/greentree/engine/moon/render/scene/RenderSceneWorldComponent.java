package com.greentree.engine.moon.render.scene;

import com.greentree.common.ecs.WorldComponent;
import com.greentree.common.renderer.scene.SceneContext;

public record RenderSceneWorldComponent(SceneContext context) implements WorldComponent {
}
