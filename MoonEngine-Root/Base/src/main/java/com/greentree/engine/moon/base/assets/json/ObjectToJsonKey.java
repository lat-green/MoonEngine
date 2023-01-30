package com.greentree.engine.moon.base.assets.json;

import java.util.Objects;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResultAssetKey;


public record ObjectToJsonKey(AssetKey object) implements AssetKey {
	
	public ObjectToJsonKey {
		Objects.requireNonNull(object);
	}
	
	public ObjectToJsonKey(Object object) {
		this(new ResultAssetKey(object));
	}
}
