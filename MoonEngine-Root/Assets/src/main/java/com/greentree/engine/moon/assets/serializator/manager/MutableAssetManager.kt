package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.data.resource.location.ResourceLocation
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.location.AssetLocation
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.NamedAssetSerializator
import com.greentree.engine.moon.assets.serializator.ResourceAssetSerializator
import java.util.function.Function

interface MutableAssetManager : AssetManager {

	fun addResourceLocation(location: ResourceLocation) {
		addSerializator(ResourceAssetSerializator(location))
	}

	fun <T : Any> addSerializator(serializator: AssetSerializator<T>)

	fun addAssetLocation(location: AssetLocation) {
		addGenerator { NamedAssetSerializator(location, it) }
	}

	fun addGenerator(generator: Function<in TypeInfo<*>, out AssetSerializator<*>>)
}