package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;
import com.greentree.engine.moon.assets.serializator.request.KeyLoadRequest;
import com.greentree.engine.moon.assets.serializator.request.builder.KeyRequestBuilder;
import com.greentree.engine.moon.assets.serializator.request.builder.KeyRequestBuilderImpl;
import com.greentree.engine.moon.assets.value.Value;

public interface ValueAssetManager extends DefaultAssetManager {

    default <T> KeyRequestBuilder<T, Value<T>> load(Class<T> cls) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return load(type);
    }

    default <T> KeyRequestBuilder<T, Value<T>> load(TypeInfo<T> type) {
        return new KeyRequestBuilderImpl<>(this::load, type);
    }

    default <T> Value<T> load(KeyLoadRequest<T> request) {
        return load(request.loadType(), request.key());
    }

    default <T> Value<T> load(Class<T> cls, AssetKey key) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return load(type, key);
    }

    default <T> Value<T> load(Class<T> cls, Object key) {
        return load(cls, new ResultAssetKey(key));
    }

    default <T> Value<T> load(Class<T> cls, String resource) {
        return load(cls, new ResourceAssetKey(resource));
    }

    default <T> Value<T> load(TypeInfo<T> type, AssetKey key) {
        return load(type, key);
    }

    default <T> Value<T> load(TypeInfo<T> type, Object key) {
        return load(type, new ResultAssetKey(key));
    }

    default <T> Value<T> load(TypeInfo<T> type, String resource) {
        return load(type, new ResourceAssetKey(resource));
    }

}
