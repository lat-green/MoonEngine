package com.greentree.engine.moon.assets.serializator.manager.cache

object HashMapCacheFactory : CacheFactory {

	override fun <K, V : Any> newCache(): Cache<K, V> = MapCache(HashMap())
}
