package com.greentree.engine.moon.assets

data class ThrowAsset<T : Any>(
	val exception: Throwable,
) : Asset<T> {

	override val value: T
		get() = throw exception
}