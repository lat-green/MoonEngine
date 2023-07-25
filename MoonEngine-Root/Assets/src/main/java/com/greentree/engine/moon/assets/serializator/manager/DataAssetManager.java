package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.serializator.request.KeyLoadRequest;
import com.greentree.engine.moon.assets.serializator.request.builder.KeyRequestBuilder;
import com.greentree.engine.moon.assets.serializator.request.builder.KeyRequestBuilderImpl;

public interface DataAssetManager {

    default <T> KeyRequestBuilder<T, T> loadData(Class<T> cls) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return loadData(type);
    }

    default <T> KeyRequestBuilder<T, T> loadData(TypeInfo<T> type) {
        return new KeyRequestBuilderImpl<>(this::loadData, type);
    }

    default <T> T loadData(KeyLoadRequest<T> request) {
        return loadData(request.loadType(), request.key());
    }

    <T> T loadData(TypeInfo<T> type, AssetKey key);

    default <T> T loadData(Class<T> cls, String resource) {
        return loadData(cls, new ResourceAssetKey(resource));
    }

    default <T> T loadData(Class<T> cls, AssetKey key) {
        return loadData(TypeInfoBuilder.getTypeInfo(cls), key);
    }

    default <T> T loadData(TypeInfo<T> type, String resource) {
        return loadData(type, new ResourceAssetKey(resource));
    }

}
