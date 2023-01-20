package com.greentree.engine.moon.base.assets.text;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResultAssetKeyImpl;


public record PropertyAssetKey(AssetKey properties, AssetKey name) implements AssetKey {
	
	public PropertyAssetKey(AssetKey properties, String name) {
		this(properties, new ResultAssetKeyImpl(name));
	}
	
}
