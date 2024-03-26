package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.exception.NotSupportedType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader

interface AssetSerializator<T : Any> {

	val type: TypeInfo<T>
		get() = TypeUtil.getFirstArgument(javaClass, AssetSerializator::class.java)

	fun load(context: AssetLoader.Context, key: AssetKey): T

	companion object
}

data class OneSerializator<T : Any>(
	private val serializator: AssetSerializator<T>,
) : AssetLoader {

	val type by serializator::type

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): T {
		if(TypeUtil.isExtends(type, this.type))
			return serializator.load(context, key) as T
		throw NotSupportedType
	}
}