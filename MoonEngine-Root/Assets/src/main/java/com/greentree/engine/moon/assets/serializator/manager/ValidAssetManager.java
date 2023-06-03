package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;

public interface ValidAssetManager {

    default boolean isValid(Class<?> cls, AssetKey key) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return isValid(type, key);
    }

    boolean isValid(TypeInfo<?> type, AssetKey key);

}
