package com.greentree.engine.moon.assets.serializator

import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load

data class SimpleAssetSerializatorWrapper<T : Any, R : Any>(
	private val origin: SimpleAssetSerializator<T, R>,
) : AssetSerializator<R> {

	private val resultType = origin.resultType
	private val sourceType = origin.sourceType
	override val type
		get() = resultType

	override fun load(manager: AssetLoader.Context, key: AssetKey): R {
		val source = manager.load(sourceType, key)
		return origin(source)
	}
}