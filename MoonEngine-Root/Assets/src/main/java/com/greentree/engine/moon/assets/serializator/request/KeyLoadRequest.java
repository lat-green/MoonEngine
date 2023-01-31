package com.greentree.engine.moon.assets.serializator.request;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.AssetKeyType;

public interface KeyLoadRequest<T> extends DefaultLoadRequest<T> {
	
	AssetKey key();
	
	@Override
	default AssetKeyType type() {
		return key().type();
	}

	T getDefault();
	
}
