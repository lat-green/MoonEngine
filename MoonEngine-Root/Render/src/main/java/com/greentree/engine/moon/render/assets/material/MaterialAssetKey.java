package com.greentree.engine.moon.render.assets.material;

import com.greentree.engine.moon.assets.key.AssetKey;

public record MaterialAssetKey(AssetKey properties, AssetKey shader) implements AssetKey {
}
