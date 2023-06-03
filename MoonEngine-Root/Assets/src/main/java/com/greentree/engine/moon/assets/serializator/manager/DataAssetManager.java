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
        return new KeyRequestBuilderImpl<>(r -> loadData(r), type);
    }

    default <T> T loadData(KeyLoadRequest<T> request) {
        return loadData(request.loadType(), request.key(), request.getDefault());
    }

    default <T> T loadData(Class<T> cls, String resource) {
        return loadData(cls, new ResourceAssetKey(resource), null);
    }

    default <T> T loadData(Class<T> cls, AssetKey key, T def) {
        return loadData(TypeInfoBuilder.getTypeInfo(cls), key, def);
    }

    <T> T loadData(TypeInfo<T> type, AssetKey key, T def);

    default <T> T loadData(Class<T> cls, AssetKey key) {
        return loadData(cls, key, null);
    }

    default <T> T loadData(TypeInfo<T> type, AssetKey key) {
        return loadData(type, key, null);
    }

}
