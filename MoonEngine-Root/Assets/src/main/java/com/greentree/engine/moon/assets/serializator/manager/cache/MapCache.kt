package com.greentree.engine.moon.assets.serializator.manager.cache

class MapCache<K, V>(private val map: MutableMap<K, V>) : Cache<K, V> {

	override fun set(key: K, create: () -> V): V {
		synchronized(this) {
			return map.getOrPut(key, create)
		}
	}

	override fun get(key: K): V? {
		synchronized(this) {
			return map[key]
		}
	}
}