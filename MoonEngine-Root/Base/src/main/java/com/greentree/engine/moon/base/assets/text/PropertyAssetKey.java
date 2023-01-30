package com.greentree.engine.moon.base.assets.text;

import java.util.Objects;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResultAssetKey;


public record PropertyAssetKey(AssetKey properties, AssetKey name) implements AssetKey {
	
	
	public PropertyAssetKey {
		Objects.requireNonNull(properties);
		Objects.requireNonNull(name);
	}
	
	public PropertyAssetKey(AssetKey properties, String name) {
		this(properties, new ResultAssetKey(name));
	}
	
}
