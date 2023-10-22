package com.greentree.engine.moon.assets.serializator.manager.cache;

public interface CacheFactory {

    <K, V> Cache<K, V> newCache();

}
