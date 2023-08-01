package com.greentree.engine.moon.assets.asset

class ThrowAsset<T : Any> constructor(private val throwable: Throwable) : Asset<T> {

	override fun isValid() = false
	override fun isConst() = true
	override fun isCache() = true

	override val value: T
		get() = throw throwable
	override val lastModified: Long
		get() = 0
}