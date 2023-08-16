package com.greentree.engine.moon.assets.asset

class ValueFunctionAsset<T : Any, R : Any> private constructor(
	private val source: Asset<T>,
	private val function: Value1Function<T, R>,
) : Asset<R> {

	override fun isValid(): Boolean {
		if(!source.isValid())
			return false
		tryUpdate()
		return exception == null
	}

	override fun isConst() = source.isConst()

	private var exception: Exception? = null
	private var cache: R? = null
	private var sourceLastUpdate = 0L

	init {
		update()
	}

	override val value: R
		get() {
			if(source.isValid())
				tryUpdate()
			if(exception != null && cache == null) {
				val e = RuntimeException(exception)
				throw e
			}
			return cache!!
		}
	override val lastModified: Long
		get() {
			if(source.isValid())
				tryUpdate()
			return sourceLastUpdate
		}

	private fun tryUpdate() {
		if(sourceLastUpdate < source.lastModified) {
			update()
		}
	}

	private fun update() {
		try {
			sourceLastUpdate = source.lastModified
			cache = function(source.value)
			exception = null
		} catch(e: Exception) {
			exception = e
		}
	}

	override fun toString(): String {
		return "Function[${function}]($source)"
	}

	companion object {

		fun <T : Any, R : Any> newAsset(
			asset: Asset<T>,
			function: Value1Function<T, R>,
		): Asset<R> {
			if(asset.isConst())
				return try {
					ConstAsset(function(asset.value))
				} catch(e: Exception) {
					ThrowAsset(e)
				}
			return ValueFunctionAsset(asset, function)
		}
	}
}