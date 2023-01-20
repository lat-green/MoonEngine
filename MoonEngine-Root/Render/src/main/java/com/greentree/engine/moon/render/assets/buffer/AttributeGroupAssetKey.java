package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.engine.moon.base.assets.array.IntArrayAssetKey;

public record AttributeGroupAssetKey(AssetKey vbo, AssetKey sizes) implements AssetKey {
	
	public AttributeGroupAssetKey(AssetKey vbo, int... sizes) {
		this(vbo, new IntArrayAssetKey(sizes));
	}
}

