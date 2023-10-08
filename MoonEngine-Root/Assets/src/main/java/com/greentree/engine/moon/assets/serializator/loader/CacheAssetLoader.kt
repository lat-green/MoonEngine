package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.manager.cache.Cache
import com.greentree.engine.moon.assets.serializator.manager.cache.CacheFactory

class CacheAssetLoader(private val origin: AssetLoader, private val factory: CacheFactory) : AssetLoader {

	private val cache = factory.newCache<TypeInfo<*>, Cache<AssetKey, Asset<*>>>()

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T> {
		return cache.set(type) {
			factory.newCache()
		}.set(key) {
			origin.load(context, type, key) as Asset<*>
		} as Asset<T>
	}

	fun <T : Any> loadCache(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T>? {
		return cache.get(type)?.get(key) as Asset<T>?
	}

	override fun toString(): String {
		return "CacheLoader($origin, $factory)"
	}
}
