package com.greentree.engine.moon.assets.serializator

import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader

class SimpleAssetSerializatorWrapper<T : Any, R : Any>(val function: SimpleAssetSerializator<T, R>) :
	AssetSerializator<R> {

	private val resultType = function.resultType
	private val sourceType = function.sourceType
	override val type
		get() = resultType

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<R> {
		val source = manager.load(sourceType, key)
		return source.map(function)
	}
}