package com.greentree.engine.moon.assets.serializator.request;

import java.util.Objects;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;

public record KeyLoadRequestImpl<T>(TypeInfo<T> loadType, AssetKey key, T def)
		implements KeyLoadRequest<T> {
	
	public KeyLoadRequestImpl {
		Objects.requireNonNull(loadType);
		Objects.requireNonNull(key);
	}
	
	public KeyLoadRequestImpl(TypeInfo<T> loadType, AssetKey key) {
		this(loadType, key, null);
	}
	
	public KeyLoadRequestImpl(Class<T> loadClass, AssetKey key) {
		this(TypeInfoBuilder.getTypeInfo(loadClass), key);
	}
	
	public KeyLoadRequestImpl(Class<T> loadClass, AssetKey key, T def) {
		this(TypeInfoBuilder.getTypeInfo(loadClass), key);
	}
	
	@Override
	public T getDefault() {
		return def;
	}
	
}
