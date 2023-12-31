package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.serializator.manager.cache.Cache
import com.greentree.engine.moon.assets.serializator.manager.cache.CacheFactory

class CacheAssetLoader(private val origin: AssetLoader, private val factory: CacheFactory) : AssetLoader {

	private val cache = factory.newCache<TypeInfo<*>, Cache<AssetKey, AssetProvider<*>>>()

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): AssetProvider<T> {
		return cache.set(type) {
			factory.newCache()
		}.set(key) {
			origin.load(context, type, key)
		} as AssetProvider<T>
	}

	fun <T : Any> loadCache(type: TypeInfo<T>, key: AssetKey): AssetProvider<T>? {
		return cache[type]?.get(key) as AssetProvider<T>?
	}

	override fun toString(): String {
		return "CacheLoader($origin, $factory)"
	}
}
