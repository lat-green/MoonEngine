package com.greentree.engine.moon.assets.cache

object HashMapCacheFactory : CacheFactory {

	override fun <K : Any, V : Any> newCache(): Cache<K, V> = MapCache(HashMap())
}
