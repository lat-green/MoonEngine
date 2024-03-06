package com.greentree.engine.moon.assets.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.SimpleAsset
import com.greentree.engine.moon.assets.cache.HashMapCacheFactory
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.CacheAssetLoader
import com.greentree.engine.moon.assets.loader.DefaultAssetLoader
import com.greentree.engine.moon.assets.loader.MultiAssetLoader
import com.greentree.engine.moon.assets.loader.ResultAssetLoader
import org.apache.logging.log4j.LogManager

class SimpleAssetManager : MutableAssetManager {

	private val loaders = mutableListOf<AssetLoader>()
	private val multiLoaders =
		CacheAssetLoader(
			MultiAssetLoader(loaders),
			HashMapCacheFactory
		)

	init {
		addLoader(DefaultAssetLoader)
		addLoader(ResultAssetLoader)
	}

	override fun addLoader(loader: AssetLoader) {
		loaders.add(loader)
	}

	override fun <T : Any> loadAsset(type: TypeInfo<T>, key: AssetKey): Asset<T> {
		val type = type.boxing
		val result = multiLoaders.load(this, type, key)
		return SimpleAsset(result)
	}

	companion object {

		private val LOG = LogManager.getLogger(
			SimpleAssetManager::class.java
		)
	}
}
