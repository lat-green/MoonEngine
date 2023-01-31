package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;
import com.greentree.engine.moon.assets.serializator.request.KeyLoadRequest;
import com.greentree.engine.moon.assets.serializator.request.builder.KeyRequestBuilder;
import com.greentree.engine.moon.assets.serializator.request.builder.KeyRequestBuilderImpl;
import com.greentree.engine.moon.assets.value.Value;

public interface ValueAssetManager extends DefaultAssetManager {
	
	default <T> KeyRequestBuilder<T, Value<T>> load(TypeInfo<T> type) {
		return new KeyRequestBuilderImpl<>(r->load(r), type);
	}
	
	default <T> KeyRequestBuilder<T, Value<T>> load(Class<T> cls) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return load(type);
	}
	
	default <T> Value<T> load(Class<T> cls, AssetKey key) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return load(type, key, null);
	}
	
	default <T> Value<T> load(Class<T> cls, AssetKey key, T def) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return load(type, key, def);
	}
	
	default <T> Value<T> load(Class<T> cls, Object key) {
		return load(cls, new ResultAssetKey(key), null);
	}
	
	default <T> Value<T> load(Class<T> cls, Object key, T def) {
		return load(cls, new ResultAssetKey(key), def);
	}
	
	default <T> Value<T> load(Class<T> cls, String resource) {
		return load(cls, new ResourceAssetKey(resource), null);
	}
	
	default <T> Value<T> load(Class<T> cls, String resource, T def) {
		return load(cls, new ResourceAssetKey(resource), def);
	}
	
	default <T> Value<T> load(TypeInfo<T> type, AssetKey key) {
		return load(type, key, null);
	}
	
	<T> Value<T> load(TypeInfo<T> type, AssetKey key, T def);
	
	default <T> Value<T> load(KeyLoadRequest<T> request) {
		return load(request.loadType(), request.key(), request.getDefault());
	}
	
	default <T> Value<T> load(TypeInfo<T> type, Object key) {
		return load(type, new ResultAssetKey(key), null);
	}
	
	default <T> Value<T> load(TypeInfo<T> type, Object key, T def) {
		return load(type, new ResultAssetKey(key), def);
	}
	
	default <T> Value<T> load(TypeInfo<T> type, String key) {
		return load(type, new ResourceAssetKey(key), null);
	}
	
	default <T> Value<T> load(TypeInfo<T> type, String key, T def) {
		return load(type, new ResourceAssetKey(key), def);
	}
	
}
