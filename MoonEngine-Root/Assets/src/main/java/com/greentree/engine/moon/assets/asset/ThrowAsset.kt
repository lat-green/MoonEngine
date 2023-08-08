package com.greentree.engine.moon.assets.asset

data class ThrowAsset<T : Any>(private val throwable: Throwable) : Asset<T> {

	override fun isValid() = false
	override fun isConst() = true

	override val value: T
		get() = throw throwable
	override val lastModified: Long
		get() = Long.MIN_VALUE
}