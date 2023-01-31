package com.greentree.engine.moon.render.assets.material;

import java.io.Serializable;

import com.greentree.engine.moon.assets.key.AssetKey;

public record MaterialData(AssetKey properties, AssetKey shader) implements Serializable {
}
