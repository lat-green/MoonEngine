package com.greentree.engine.moon.assets.location;

import com.greentree.engine.moon.assets.key.AssetKey;

public interface AssetLocation {
	
	AssetKey getKey(String name);
	
	default boolean isExist(String name) {
		return getKey(name) != null;
	}
	
}
