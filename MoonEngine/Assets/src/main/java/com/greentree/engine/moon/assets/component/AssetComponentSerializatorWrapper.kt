package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.manager.MutableAssetManager

data class AssetComponentSerializatorWrapper<T : Any>(val origin: AssetComponentSerializator<T>) :
	AssetSerializator<T> {

	override val type: TypeInfo<T>
		get() = origin.type

	override fun load(context: AssetLoader.Context, key: AssetKey) =
		ComponentAsset(context) { origin.load(it, key) }
}

fun MutableAssetManager.addSerializator(component: AssetComponentSerializator<*>) =
	addSerializator(AssetComponentSerializatorWrapper(component))

