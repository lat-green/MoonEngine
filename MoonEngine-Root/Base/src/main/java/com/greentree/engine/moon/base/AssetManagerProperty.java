package com.greentree.engine.moon.base;

import com.greentree.commons.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.module.EngineProperty;


public record AssetManagerProperty(AssetManager manager) implements EngineProperty {
}
