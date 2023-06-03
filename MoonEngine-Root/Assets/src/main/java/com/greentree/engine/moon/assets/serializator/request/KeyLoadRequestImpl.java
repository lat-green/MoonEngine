package com.greentree.engine.moon.assets.serializator.request;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;

import java.util.Objects;

public record KeyLoadRequestImpl<T>(TypeInfo<T> loadType, AssetKey key, T def)
        implements KeyLoadRequest<T> {

    public KeyLoadRequestImpl(Class<T> loadClass, AssetKey key) {
        this(TypeInfoBuilder.getTypeInfo(loadClass), key);
    }

    public KeyLoadRequestImpl(TypeInfo<T> loadType, AssetKey key) {
        this(loadType, key, null);
    }

    public KeyLoadRequestImpl {
        Objects.requireNonNull(loadType);
        Objects.requireNonNull(key);
    }

    public KeyLoadRequestImpl(Class<T> loadClass, AssetKey key, T def) {
        this(TypeInfoBuilder.getTypeInfo(loadClass), key);
    }

    @Override
    public T getDefault() {
        return def;
    }

}
