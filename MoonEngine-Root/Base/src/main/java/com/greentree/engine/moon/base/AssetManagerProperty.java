package com.greentree.engine.moon.base;

import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.ecs.scene.SceneProperty;

public record AssetManagerProperty(AssetManager manager) implements SceneProperty {

}
