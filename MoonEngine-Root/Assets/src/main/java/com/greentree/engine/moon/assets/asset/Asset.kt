package com.greentree.engine.moon.assets.asset

import java.io.Serializable

interface Asset<out T : Any> : Serializable {

	val value: T
	val lastModified: Long

	fun isConst(): Boolean = false
	fun isCache(): Boolean = false
}

inline fun <T : Any, R : Any> Asset<T>.map(function: Value1Function<T, R>) = ValueFunctionAsset.newAsset(this, function)
inline fun <T : Any, R : Any, F> Asset<T>.map(function: F) where F : (T) -> R, F : Serializable =
	MapAsset.newAsset(this, function)