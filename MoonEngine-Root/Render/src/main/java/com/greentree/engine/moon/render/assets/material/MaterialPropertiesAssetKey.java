package com.greentree.engine.moon.render.assets.material;

import java.util.Map;

import com.greentree.engine.moon.assets.key.AssetKey;


public record MaterialPropertiesAssetKey(Map<String, ? extends AssetKey> properties) implements AssetKey {
}
