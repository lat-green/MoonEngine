package com.greentree.engine.moon.base;

import com.greentree.engine.moon.assets.serializator.manager.MutableAssetManager;
import com.greentree.engine.moon.ecs.scene.SceneProperty;

public record AssetManagerProperty(MutableAssetManager manager) implements SceneProperty {

}
