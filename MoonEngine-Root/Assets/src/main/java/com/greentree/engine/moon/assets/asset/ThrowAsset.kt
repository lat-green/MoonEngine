package com.greentree.engine.moon.assets.asset

data class ThrowAsset<T : Any>(val exception: Exception) : Asset<T> {

	override fun isValid() = false
	override fun isConst() = true

	override fun toString(): String {
		return "ThrowAsset[$exception]"
	}

	override val value: T
		get() = throw RuntimeException(exception)
	override val lastModified: Long
		get() = Long.MIN_VALUE
}