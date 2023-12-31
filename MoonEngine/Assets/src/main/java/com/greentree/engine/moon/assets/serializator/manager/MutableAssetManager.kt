package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.data.resource.location.ResourceLocation
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.location.AssetLocation
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.ResourceAssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.DefaultLoader
import com.greentree.engine.moon.assets.serializator.loader.Generator
import com.greentree.engine.moon.assets.serializator.loader.NamedAssetLoader
import com.greentree.engine.moon.assets.serializator.loader.OneSerializator
import java.util.function.Function

interface MutableAssetManager : ChainAssetManager {

	fun addLoader(loader: AssetLoader)
	fun addDefaultLoader(loader: DefaultLoader)

	fun addResourceLocation(location: ResourceLocation) {
		addSerializator(ResourceAssetSerializator(location))
	}

	fun <T : Any> addSerializator(serializator: AssetSerializator<T>) = addLoader(OneSerializator(serializator))

	fun addAssetLocation(location: AssetLocation) {
		addLoader(NamedAssetLoader(location))
	}

	@Deprecated("use addLoader")
	fun addGenerator(generator: Function<in TypeInfo<*>, out AssetSerializator<*>>) = addLoader(Generator(generator))
}
