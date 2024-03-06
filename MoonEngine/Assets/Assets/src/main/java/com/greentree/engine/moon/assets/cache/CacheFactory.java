package com.greentree.engine.moon.assets.cache;

public interface CacheFactory {

    <K, V> Cache<K, V> newCache();

}
