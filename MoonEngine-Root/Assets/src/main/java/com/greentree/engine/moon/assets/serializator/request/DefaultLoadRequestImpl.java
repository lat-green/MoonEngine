package com.greentree.engine.moon.assets.serializator.request;

import java.util.Objects;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKeyType;

public record DefaultLoadRequestImpl<T>(TypeInfo<T> loadType, AssetKeyType type)
		implements DefaultLoadRequest<T> {
	
	public DefaultLoadRequestImpl {
		Objects.requireNonNull(loadType);
		Objects.requireNonNull(type);
	}
	
	public DefaultLoadRequestImpl(Class<T> loadClass, AssetKeyType type) {
		this(TypeInfoBuilder.getTypeInfo(loadClass), type);
	}
	
	public DefaultLoadRequestImpl(TypeInfo<T> loadType) {
		this(loadType, AssetKeyType.DEFAULT);
	}
	
	public DefaultLoadRequestImpl(Class<T> loadClass) {
		this(TypeInfoBuilder.getTypeInfo(loadClass));
	}
	
}
