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

public interface AsyncAssetManager extends ValueAssetManager {

    default <T> KeyRequestBuilder<T, Value<T>> loadAsync(Class<T> cls) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return loadAsync(type);
    }

    default <T> KeyRequestBuilder<T, Value<T>> loadAsync(TypeInfo<T> type) {
        return new KeyRequestBuilderImpl<>(r -> loadAsync(r), type);
    }

    default <T> Value<T> loadAsync(KeyLoadRequest<T> request) {
        return loadAsync(request.loadType(), request.key(), request.getDefault());
    }

    <T> Value<T> loadAsync(TypeInfo<T> type, AssetKey key, T def);

    default <T> Value<T> loadAsync(Class<T> cls, AssetKey key) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return loadAsync(type, key, loadDefault(type));
    }

    default <T> Value<T> loadAsync(TypeInfo<T> type, AssetKey key) {
        return loadAsync(type, key, loadDefault(type));
    }

    default <T> Value<T> loadAsync(Class<T> cls, String path) {
        return loadAsync(cls, new ResourceAssetKey(path), loadDefault(cls));
    }

    default <T> Value<T> loadAsync(Class<T> cls, AssetKey key, T def) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return loadAsync(type, key, loadDefault(type, def));
    }

    default <T> Value<T> loadAsync(Class<T> cls, String path, T def) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return loadAsync(type, new ResourceAssetKey(path), loadDefault(type, def));
    }

    default <T> Value<T> loadAsync(TypeInfo<T> type, String path) {
        return loadAsync(type, new ResourceAssetKey(path), loadDefault(type));
    }

    default <T> Value<T> loadAsync(TypeInfo<T> type, String path, T def) {
        return loadAsync(type, new ResourceAssetKey(path), def);
    }

    default <T> Value<T> loadAsync(Class<T> cls, Object obj) {
        return loadAsync(cls, new ResultAssetKey(obj), loadDefault(cls));
    }

    default <T> Value<T> loadAsync(Class<T> cls, Object obj, T def) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return loadAsync(type, new ResultAssetKey(obj), loadDefault(type, def));
    }

    default <T> Value<T> loadAsync(TypeInfo<T> type, Object obj) {
        return loadAsync(type, new ResultAssetKey(obj), loadDefault(type));
    }

    default <T> Value<T> loadAsync(TypeInfo<T> type, Object obj, T def) {
        return loadAsync(type, new ResultAssetKey(obj), def);
    }

}
