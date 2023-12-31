package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
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
import com.greentree.engine.moon.assets.serializator.manager.chain.Chain
import com.greentree.engine.moon.assets.serializator.manager.chain.ChainHandler
import org.apache.logging.log4j.LogManager

class BaseAssetManager : MutableAssetManager, AssetLoader.Context {

	private val defaultLoaders = mutableListOf<DefaultLoader>()
	private val multiDefaultLoaders = MultiDefaultAssetLoader(defaultLoaders)
	private val loaders = mutableListOf<AssetLoader>()
	private val multiLoaders =
		CacheAssetLoader(
//			DebugAssetLoader(
			MultiAssetLoader(loaders),
//			),
			HashMapCacheFactory
		)

	init {
		addLoader(DefaultAssetLoader())
		addLoader(ResultAssetLoader())
	}

	override fun addLoader(loader: AssetLoader) {
		loaders.add(loader)
	}

	override fun addDefaultLoader(loader: DefaultLoader) {
		defaultLoaders.add(loader)
	}

	override fun build(ctx: ChainHandler): Chain = BaseChain(ctx)

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey) = multiLoaders.load(this, type, key)

	private inner class BaseChain(val ctx: ChainHandler) : Chain {

		val root = RootChain(this)

		override fun <T : Any> loadDefault(type: TypeInfo<T>, key: AssetKeyType) =
			ctx.loadDefault(root, type, key)

		override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey) = ctx.load(root, type, key)
		override fun <T : Any> loadCache(type: TypeInfo<T>, key: AssetKey) = ctx.loadCache(root, type, key)
	}

	private inner class RootChain(val chain: Chain) : Chain {

		override fun <T : Any> loadDefault(type: TypeInfo<T>, key: AssetKeyType) =
			multiDefaultLoaders.load(chain, type, key)

		override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey) = multiLoaders.load(chain, type, key)
		override fun <T : Any> loadCache(type: TypeInfo<T>, key: AssetKey) = multiLoaders.loadCache(type, key)
	}

	companion object {

		private val LOG = LogManager.getLogger(
			BaseAssetManager::class.java
		)
	}
}
