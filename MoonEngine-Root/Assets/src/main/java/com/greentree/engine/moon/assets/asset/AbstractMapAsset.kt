package com.greentree.engine.moon.assets.asset

abstract class AbstractMapAsset<T : Any, R : Any>(val asset: Asset<T>) :
	Asset<R> {

	override val value: R
		get() = map(asset.value)
	override val lastModified: Long
		get() = asset.lastModified

	abstract fun map(value: T): R

	override fun isConst() = asset.isConst()
	override fun isCache() = false
}