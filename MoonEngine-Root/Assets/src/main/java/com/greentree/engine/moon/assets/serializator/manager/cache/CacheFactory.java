package com.greentree.engine.moon.assets.serializator.manager.cache;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.key.AssetKey;

public interface CacheFactory {

    <T> Cache<AssetKey, Asset<T>> newCache();

}
