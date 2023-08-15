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
	override var cache = function(source.cache)
		private set
	override val value: R
		get() {
			tryUpdate()
			if(exception != null)
				throw exception as Exception
			return cache
		}

	private inline fun tryUpdate() {
		if(sourceLastUpdate < source.lastModified) {
			sourceLastUpdate = source.lastModified
			try {
				cache = function(source.value)
			} catch(e: Exception) {
				exception = e
			}
		}
	}

	override val lastModified: Long
		get() {
			tryUpdate()
			return sourceLastUpdate
		}
	private var sourceLastUpdate = source.lastModified

	override fun toString(): String {
		return "Function[${function}]($source)"
	}

	companion object {

		fun <T : Any, R : Any> newAsset(
			asset: Asset<T>,
			function: Value1Function<T, R>,
		): Asset<R> {
			if(asset.isConst())
				return ConstAsset(function(asset.value))
			return ValueFunctionAsset(asset, function)
		}
	}
}