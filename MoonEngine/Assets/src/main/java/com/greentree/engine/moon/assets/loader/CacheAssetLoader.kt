package com.greentree.engine.moon.assets.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.manager.cache.CacheFactory

data class CacheAssetLoader(
	private val origin: AssetLoader,
	private val cacheFactory: CacheFactory,
) : AssetLoader {

	private val cache = cacheFactory.newCache<Pair<TypeInfo<*>, AssetKey>, Any>()

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): T {
		return cache.set(type to key) {
			origin.load(context, type, key)
		}
	}
}