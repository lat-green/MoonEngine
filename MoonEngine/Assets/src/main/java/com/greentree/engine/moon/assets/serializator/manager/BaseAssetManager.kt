package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.CacheAssetLoader
import com.greentree.engine.moon.assets.serializator.loader.DefaultAssetLoader
import com.greentree.engine.moon.assets.serializator.loader.DefaultLoader
import com.greentree.engine.moon.assets.serializator.loader.MultiAssetLoader
import com.greentree.engine.moon.assets.serializator.loader.MultiDefaultAssetLoader
import com.greentree.engine.moon.assets.serializator.loader.ResultAssetLoader
import com.greentree.engine.moon.assets.serializator.manager.cache.HashMapCacheFactory
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.message.SimpleMessage

class BaseAssetManager : MutableAssetManager, AsyncAssetManager {

	private val defaultLoaders = mutableListOf<DefaultLoader>()
	private val loaders = mutableListOf<AssetLoader>()
	private val multiDefaultLoaders = MultiDefaultAssetLoader(defaultLoaders)
	private val multiLoaders =
		CacheAssetLoader(
			MultiAssetLoader(loaders),
			HashMapCacheFactory
		)

	init {
		addLoader(DefaultAssetLoader())
		addLoader(ResultAssetLoader())
	}

	private inner class AssetLoaderContext : AssetLoader.Context {

		override fun <T : Any> loadDefault(type: TypeInfo<T>, key: AssetKeyType) =
			this@BaseAssetManager.loadDefault(type, key)

		override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T> {
			val result = multiLoaders.load(context, type, key)
			LOG.debug(AssetManager.ASSETS) { SimpleMessage("type: $type key: $key result: $result") }
			return result
		}
	}

	private inner class DefaultLoaderContext : DefaultLoader.Context {

		override fun <T : Any> load(type: TypeInfo<T>, key: AssetKeyType): T? {
			return multiDefaultLoaders.load(this, type, key)
		}
	}

	private val defaultContext = DefaultLoaderContext()
	private val context = AssetLoaderContext()

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey) =
		multiLoaders.load(context, type, key)
	
	override fun <T : Any> loadCache(type: TypeInfo<T>, key: AssetKey) =
		multiLoaders.loadCache(context, type, key)

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey) = context.load(type, key)
	override fun <T : Any> loadDefault(type: TypeInfo<T>, key: AssetKeyType) = defaultContext.load(type, key)

	override fun addLoader(loader: AssetLoader) {
		loaders.add(loader)
	}

	override fun addDefaultLoader(loader: DefaultLoader) {
		defaultLoaders.add(loader)
	}

	companion object {

		private val LOG = LogManager.getLogger(
			BaseAssetManager::class.java
		)
	}
}