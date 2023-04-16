package com.greentree.engine.moon.base;

import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.modules.EngineProperty;


public record AssetManagerProperty(AssetManager manager) implements EngineProperty, WorldComponent {
}
