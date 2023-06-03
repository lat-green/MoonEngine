package com.greentree.engine.moon.base.scene;

import com.greentree.engine.moon.ecs.scene.SceneProperty;
import com.greentree.engine.moon.modules.property.EngineProperties;

public record EnginePropertiesSceneProperty(EngineProperties properties) implements SceneProperty {

}
