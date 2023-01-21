package com.greentree.engine.moon.render.scene;

import com.greentree.common.renderer.scene.SceneContext;
import com.greentree.engine.moon.ecs.WorldComponent;

public record RenderSceneWorldComponent(SceneContext context) implements WorldComponent {
}
