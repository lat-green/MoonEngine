package com.greentree.engine.moon.render.assets.material;

import com.greentree.commons.assets.key.AssetKey;

public record MaterialAssetKey(AssetKey properties, AssetKey shader) implements AssetKey {
}
