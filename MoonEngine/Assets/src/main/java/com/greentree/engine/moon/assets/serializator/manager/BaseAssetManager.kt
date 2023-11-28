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

class BaseAssetManager : MutableAssetManager, AssetLoader.Context, DefaultLoader.Context {

	private val defaultLoaders = mutableListOf<DefaultLoader>()
	private val multiDefaultLoaders = MultiDefaultAssetLoader(defaultLoaders)
	private val loaders = mutableListOf<AssetLoader>()
	private val multiLoaders =
//		DebugAssetLoader(
		CacheAssetLoader(
			MultiAssetLoader(loaders),
			HashMapCacheFactory
		)

	//		)
	init {
		addLoader(DefaultAssetLoader())
		addLoader(ResultAssetLoader())
	}

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey) =
		multiLoaders.load(context, type, key)

	override fun <T : Any> loadCache(type: TypeInfo<T>, key: AssetKey) =
		multiLoaders.loadCache(this, type, key)

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T> {
		return load(this, type, key)
	}

	override fun <T : Any> load(cls: Class<T>, key: AssetKey) = super<MutableAssetManager>.load(cls, key)

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKeyType) = multiDefaultLoaders.load(this, type, key)
	override fun <T : Any> loadDefault(type: TypeInfo<T>, key: AssetKeyType) = multiDefaultLoaders.load(this, type, key)

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
