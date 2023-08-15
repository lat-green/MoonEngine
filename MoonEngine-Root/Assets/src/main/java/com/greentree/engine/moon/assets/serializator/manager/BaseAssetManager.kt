package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.DefaultAssetLoader
import com.greentree.engine.moon.assets.serializator.ResultAssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.CacheAssetLoader
import com.greentree.engine.moon.assets.serializator.loader.MultiAssetLoader
import com.greentree.engine.moon.assets.serializator.loader.NotNullAssetLoader
import com.greentree.engine.moon.assets.serializator.loader.NotThrowAssetLoader
import com.greentree.engine.moon.assets.serializator.manager.cache.WeakHashMapCacheFactory
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.message.SimpleMessage

class BaseAssetManager : MutableAssetManager {

	private val loaders = mutableListOf<AssetLoader>()
	private val multiLoaders =
		NotNullAssetLoader(
			CacheAssetLoader(
				NotThrowAssetLoader(MultiAssetLoader(loaders)),
				WeakHashMapCacheFactory()
			)
		)

	init {
		addLoader(DefaultAssetLoader())
		addGenerator { ResultAssetSerializator(it) }
	}

	private inner class AssetLoaderContext : AssetLoader.Context {

		override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey) = this@BaseAssetManager.load(type, key)
	}

	private val context = AssetLoaderContext()

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T> {
		val result = multiLoaders.load(context, type, key)
		LOG.debug(AssetManager.ASSETS) { SimpleMessage("type: $type key: $key result: $result") }
		return result
	}

	override fun addLoader(loader: AssetLoader) {
		loaders.add(loader)
	}

	companion object {

		private val LOG = LogManager.getLogger(
			BaseAssetManager::class.java
		)
	}
}