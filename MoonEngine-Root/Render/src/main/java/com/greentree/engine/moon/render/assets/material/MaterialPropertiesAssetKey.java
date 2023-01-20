package com.greentree.engine.moon.render.assets.material;

import java.util.Map;

import com.greentree.commons.assets.key.AssetKey;


public record MaterialPropertiesAssetKey(Map<String, ? extends AssetKey> properties) implements AssetKey {
}
