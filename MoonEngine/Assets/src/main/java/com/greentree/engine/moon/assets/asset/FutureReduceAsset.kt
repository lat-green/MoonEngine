package com.greentree.engine.moon.assets.asset

import kotlin.math.max

class FutureReduceAsset<T : Any>(
	val default: T,
) : Asset<T> {

	private var isInitialized = false
	private var origin: Result<Asset<T>> = Result.failure(NullPointerException())
	private var lastLoad = System.currentTimeMillis()

	fun setValue(value: Asset<T>) {
		lastLoad = System.currentTimeMillis()
		origin = Result.success(value)
		isInitialized = true
	}

	fun setException(e: Throwable) {
		lastLoad = System.currentTimeMillis()
		origin = Result.failure(e)
		isInitialized = true
	}

	override val value: T
		get() {
			if(isInitialized)
				return origin.getOrThrow().value
			return default
		}
	override val lastModified
		get() = if(isInitialized)
			max(origin.getOrNull()?.lastModified ?: 0, lastLoad)
		else
			lastLoad

	override fun isConst() = isInitialized && origin.getOrNull()?.isConst() ?: true
}
