package com.greentree.engine.moon.assets.serializator.request;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;

import java.util.Objects;

public record KeyLoadRequestImpl<T>(TypeInfo<T> loadType, AssetKey key)
        implements KeyLoadRequest<T> {

    public KeyLoadRequestImpl(Class<T> loadClass, AssetKey key) {
        this(TypeInfoBuilder.getTypeInfo(loadClass), key);
    }

    public KeyLoadRequestImpl {
        loadType = loadType.getBoxing();
        Objects.requireNonNull(loadType);
        Objects.requireNonNull(key);
    }

}
