package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.Asset
import com.greentree.engine.moon.assets.SimpleAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.CacheAssetLoader
import com.greentree.engine.moon.assets.loader.DefaultAssetLoader
import com.greentree.engine.moon.assets.loader.MultiAssetLoader
import com.greentree.engine.moon.assets.loader.ResultAssetLoader
import com.greentree.engine.moon.assets.serializator.manager.cache.HashMapCacheFactory
import org.apache.logging.log4j.LogManager

class SimpleAssetManager : MutableAssetManager {

	private val context = SimpleAssetLoaderContext()
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

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T> {
		val type = type.boxing
//		return runCatching {
//			multiLoaders.load(context, type, key)
//		}.map {
//			SimpleAsset(it)
//		}.getOrElse {
//			ThrowAsset(it)
//		}
		val result = multiLoaders.load(context, type, key)
		return SimpleAsset(result)
	}

	companion object {

		private val LOG = LogManager.getLogger(
			SimpleAssetManager::class.java
		)
	}

	private inner class SimpleAssetLoaderContext : AssetLoader.Context {

		override fun <T : Any> loadAsset(type: TypeInfo<T>, key: AssetKey) = this@SimpleAssetManager.load(type, key)
	}
}
