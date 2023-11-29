package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.util.function.SaveFunction
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import java.util.function.Function

class Generator(generator: Function<in TypeInfo<*>, out AssetSerializator<*>>) :
	AssetLoader {

	private val serializators = object : SaveFunction<TypeInfo<*>, AssetSerializator<*>>() {
		override fun create(type: TypeInfo<*>) = generator.apply(type)
	}

	override fun <T : Any> load(manager: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T> {
		return (serializators.apply(type) as AssetSerializator<T>).load(manager, key)
	}
}
