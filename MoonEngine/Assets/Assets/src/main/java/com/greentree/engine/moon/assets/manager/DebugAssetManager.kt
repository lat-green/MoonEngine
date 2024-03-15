package com.greentree.engine.moon.assets.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader

data class DebugAssetManager(
	val origin: MutableAssetManager,
	val debugCallBack: DebugCallback,
) : MutableAssetManager {

	fun interface DebugCallback {

		fun loadAsset(type: TypeInfo<*>, key: AssetKey): () -> Unit
	}

	override fun addLoader(loader: AssetLoader) {
		origin.addLoader(loader)
	}

	override fun <T : Any> loadAsset(type: TypeInfo<T>, key: AssetKey): Asset<T> {
		val callback = debugCallBack.loadAsset(type, key)
		try {
			return origin.loadAsset(type, key)
		} finally {
			callback()
		}
	}
}