package com.greentree.engine.moon.assets.asset

class CacheFunctionAsset<T : Any, R : Any> private constructor(
	private val source: Asset<T>,
	private val function: Value1Function<T, R>,
) : Asset<R> {

	override fun isValid(): Boolean {
		if(!source.isValid())
			return false
		tryUpdate()
		return (exception == null)
	}

	override fun isConst() = source.isConst()

	private var needUpdate: Boolean = false
	private var exception: Exception? = null
	private var cache: R? = null
	private var sourceLastUpdate = 0L

	init {
		try {
			sourceLastUpdate = source.lastModified
			cache = function(source.value)
			exception = null
		} catch(e: Exception) {
			cache = null
			exception = e
		}
	}

	override val value: R
		get() {
			try {
				if(exception != null && cache == null)
					throw RuntimeException(exception)
				return cache!!
			} finally {
				tryUpdate()
			}
		}
	override val lastModified: Long
		get() {
			trySetUpdate()
			return sourceLastUpdate
		}

	private fun trySetUpdate() {
		if(!needUpdate && source.isValid() && source.isChange(sourceLastUpdate)) {
			sourceLastUpdate = source.lastModified
			needUpdate = true
		}
	}

	private fun tryUpdate() {
		trySetUpdate()
		if(needUpdate) {
			needUpdate = false
			try {
				cache = function(source.value)
				exception = null
			} catch(e: Exception) {
				exception = e
			}
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
			if(asset is ThrowAsset)
				return asset as Asset<R>
			return CacheFunctionAsset(asset, function)
		}
	}
}