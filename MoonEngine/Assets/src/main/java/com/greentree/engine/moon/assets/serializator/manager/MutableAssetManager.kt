package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.data.resource.location.ResourceLocation
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.OneSerializator
import com.greentree.engine.moon.assets.serializator.ResourceAssetSerializator

interface MutableAssetManager : AssetManager {

	fun addLoader(loader: AssetLoader)

	fun addResourceLocation(location: ResourceLocation) {
		addSerializator(ResourceAssetSerializator(location))
	}

	fun <T : Any> addSerializator(serializator: AssetSerializator<T>) = addLoader(OneSerializator(serializator))
}
