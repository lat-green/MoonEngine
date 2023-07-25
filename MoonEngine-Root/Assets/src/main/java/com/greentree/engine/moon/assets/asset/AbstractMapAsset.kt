package com.greentree.engine.moon.assets.asset

abstract class AbstractMapAsset<T : Any, R : Any>(val asset: Asset<T>) :
	Asset<R> {

	override val value: R
		get() = function(asset.value)
	abstract val function: (T) -> R
	override val lastModified: Long
		get() = asset.lastModified

	override fun isConst() = asset.isConst()
	override fun isCache() = false
}