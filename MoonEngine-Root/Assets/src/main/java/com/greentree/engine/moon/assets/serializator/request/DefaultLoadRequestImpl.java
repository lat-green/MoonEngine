package com.greentree.engine.moon.assets.serializator.request;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKeyType;

import java.util.Objects;

public record DefaultLoadRequestImpl<T>(TypeInfo<T> loadType, AssetKeyType type)
        implements DefaultLoadRequest<T> {

    public DefaultLoadRequestImpl(Class<T> loadClass, AssetKeyType type) {
        this(TypeInfoBuilder.getTypeInfo(loadClass), type);
    }

    public DefaultLoadRequestImpl {
        Objects.requireNonNull(loadType);
        Objects.requireNonNull(type);
    }

    public DefaultLoadRequestImpl(Class<T> loadClass) {
        this(TypeInfoBuilder.getTypeInfo(loadClass));
    }

    public DefaultLoadRequestImpl(TypeInfo<T> loadType) {
        this(loadType, AssetKeyType.DEFAULT);
    }

}
