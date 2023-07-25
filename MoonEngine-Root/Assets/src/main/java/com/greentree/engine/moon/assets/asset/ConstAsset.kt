package com.greentree.engine.moon.assets.asset

class ConstAsset<T : Any>(override val value: T) : Asset<T> {

	override val lastModified: Long
		get() = 0

	override fun isConst() = true
	override fun isCache() = true
}