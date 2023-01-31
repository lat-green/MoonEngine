package com.greentree.engine.moon.assets.key;

import com.greentree.commons.util.iterator.IteratorUtil;


public record DefaultAssetKey(Iterable<? extends AssetKey> keys) implements AssetKey {
	
	public DefaultAssetKey(AssetKey... keys) {
		this(IteratorUtil.iterable(keys));
	}
	
}
