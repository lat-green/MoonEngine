package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.manager.cache.Cache
import com.greentree.engine.moon.assets.serializator.manager.cache.CacheFactory
import com.greentree.engine.moon.assets.serializator.manager.cache.HashMapCache

class CacheAssetLoader(private val origin: AssetLoader, factory: CacheFactory) : AssetLoader {

	private val factory: CacheMap

	init {
		this.factory = factory.cache()
	}

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T>? {
		val cache = factory[type]
		return cache.set(key) {
			origin.load(context, type, key)
		}
	}
}

private fun CacheFactory.cache(): CacheMap {
	return object : CacheMap {
		val map = HashMapCache<TypeInfo<*>, Cache<AssetKey, Asset<*>>>()

		override fun <T : Any> get(type: TypeInfo<T>): Cache<AssetKey, Asset<T>> {
			return map.set(type) {
				this@cache.newCache()
			} as Cache<AssetKey, Asset<T>>
		}
	}
}

private interface CacheMap {

	operator fun <T : Any> get(type: TypeInfo<T>): Cache<AssetKey, Asset<T>>
}
