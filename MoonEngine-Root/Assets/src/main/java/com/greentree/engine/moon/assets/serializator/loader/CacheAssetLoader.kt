package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.cache.Cache
import com.greentree.engine.moon.assets.serializator.manager.cache.CacheFactory

class CacheAssetLoader(private val origin: AssetLoader, factory: CacheFactory) : AssetLoader {

	private val factory: CacheMap

	init {
		this.factory = factory.cache()
	}

	override fun <T : Any> load(manager: AssetManager, type: TypeInfo<T>, key: AssetKey): Asset<T>? {
		val cache = factory[type]
		return cache.set(key) {
			origin.load(manager, type, key)
		}
	}
}

private fun CacheFactory.cache(): CacheMap {
	return object : CacheMap {
		val map = mutableMapOf<TypeInfo<*>, Cache<AssetKey, Asset<*>>>()

		override fun <T : Any> get(type: TypeInfo<T>): Cache<AssetKey, Asset<T>> {
			if(type in map)
				return (map[type] as Cache<AssetKey, Asset<T>>)
			val result = this@cache.newCache<T>()
			map[type] = result as Cache<AssetKey, Asset<*>>
			return result
		}
	}
}

private interface CacheMap {

	operator fun <T : Any> get(type: TypeInfo<T>): Cache<AssetKey, Asset<T>>
}
