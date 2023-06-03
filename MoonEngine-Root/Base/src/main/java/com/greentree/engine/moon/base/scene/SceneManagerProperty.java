package com.greentree.engine.moon.base.scene;

import com.greentree.engine.moon.ecs.scene.SceneProperty;
import com.greentree.engine.moon.modules.property.EngineProperty;

public record SceneManagerProperty(SceneManager manager) implements EngineProperty, SceneProperty {

}
