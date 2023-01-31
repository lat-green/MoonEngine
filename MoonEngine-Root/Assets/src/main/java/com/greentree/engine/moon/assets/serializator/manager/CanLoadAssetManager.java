package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;

public interface CanLoadAssetManager extends ValidAssetManager {
	
	@Override
	default boolean isValid(TypeInfo<?> type, AssetKey key) {
		return canLoad(type, key);
	}
	
	default boolean canLoad(Class<?> cls, AssetKey key) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return canLoad(type, key);
	}
	
	boolean canLoad(TypeInfo<?> type, AssetKey key);
	
}
