package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKey;

public interface AssetManagerBase extends ValueAssetManager, DataAssetManager {

    default <T> T loadData(TypeInfo<T> type, AssetKey key, T def) {
        return load(type, key, def).get();
    }

}
