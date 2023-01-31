package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;

public interface DeepValidAssetManager extends ValidAssetManager {
	
	default boolean isDeepValid(Class<?> cls, AssetKey key) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return isDeepValid(type, key);
	}
	
	default boolean isDeepValid(TypeInfo<?> type, AssetKey key) {
		return isValid(type, key);
	}
	
	
	
}
