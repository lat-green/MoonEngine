package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.IterableAssetKeyImpl;
import com.greentree.engine.moon.assets.key.NullAssetKey;
import com.greentree.engine.moon.base.assets.array.IntArrayAssetKey;

public record VAOAssetKey(AssetKey ebo, AssetKey vbos) implements AssetKey {

	public static VAOAssetKey create(AttributeGroupAssetKey...vbos) {
		return new VAOAssetKey(new NullAssetKey(), new IterableAssetKeyImpl(vbos));
	}
	
	public static VAOAssetKey create(IntArrayAssetKey ebo, AttributeGroupAssetKey...vbos) {
		return new VAOAssetKey(ebo, new IterableAssetKeyImpl(vbos));
	}
	
}
