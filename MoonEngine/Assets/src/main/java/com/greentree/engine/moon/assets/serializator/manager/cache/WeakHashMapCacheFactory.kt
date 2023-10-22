package com.greentree.engine.moon.assets.serializator.manager.cache

import java.util.WeakHashMap

object WeakHashMapCacheFactory : CacheFactory {

	override fun <K, T : Any> newCache(): Cache<K, T> = MapCache(WeakHashMap())
}
