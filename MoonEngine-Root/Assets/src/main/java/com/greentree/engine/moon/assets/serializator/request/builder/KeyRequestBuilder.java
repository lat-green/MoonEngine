package com.greentree.engine.moon.assets.serializator.request.builder;

import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;

public interface KeyRequestBuilder<T, R> extends RequestBuilder<R> {

    default KeyRequestBuilder<T, R> set(Class<T> cls) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return set(type);
    }

    default KeyRequestBuilder<T, R> set(Object resultValue) {
        final var key = new ResultAssetKey(resultValue);
        return set(key);
    }

    KeyRequestBuilder<T, R> set(AssetKey key);

    default KeyRequestBuilder<T, R> setResource(String resource) {
        final var key = new ResourceAssetKey(resource);
        return set(key);
    }

    KeyRequestBuilder<T, R> setDefault(T def);

}
