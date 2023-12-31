package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.manager.MutableAssetManager

data class AssetComponentLoaderWrapper(val origin: AssetComponentLoader) : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey) =
		ComponentAsset(context) { origin.load(it, type, key) }
}

fun MutableAssetManager.addLoader(component: AssetComponentLoader) = addLoader(AssetComponentLoaderWrapper(component))
