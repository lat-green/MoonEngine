package com.greentree.engine.moon.assets.component

interface AssetNode<T : Any> {

	val value: T
}

data class Result<T : Any>(override val value: T) : AssetNode<T>

interface AssetComponent<T : Any> {

	fun value(): AssetNode<T>
}

val <T : Any> AssetComponent<T>.value
	get() = value().value

