package com.greentree.engine.moon.assets.serializator.manager.cache

import java.util.*

object WeakHashMapCacheFactory : CacheFactory {

	override fun <K : Any, T : Any> newCache(): Cache<K, T> = MapCache(WeakHashMap())
}
