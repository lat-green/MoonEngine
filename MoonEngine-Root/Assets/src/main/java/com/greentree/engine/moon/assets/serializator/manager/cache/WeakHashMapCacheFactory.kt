package com.greentree.engine.moon.assets.serializator.manager.cache

import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey

class WeakHashMapCacheFactory : CacheFactory {

	override fun <T : Any> newCache(): Cache<AssetKey, Asset<T>> = WeakHashMapCache()
}
