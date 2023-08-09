package com.greentree.engine.moon.assets.asset

import org.apache.logging.log4j.LogManager

private val LOG = LogManager.getLogger(ValueFunctionAsset::class.java)

class ValueFunctionAsset<T : Any, R : Any> private constructor(
	private val source: Asset<T>,
	private val function: Value1Function<T, R>,
) : Asset<R> {

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

	override fun isConst() = source.isConst()

	private var cache: R = function(source.value)
	override val value: R
		get() {
			tryUpdate()
			return cache
		}

	private inline fun tryUpdate() {
		if(_lastModified < source.lastModified) {
			_lastModified = source.lastModified
			cache = function(source.value, cache)
		}
	}

	override val lastModified: Long
		get() {
			tryUpdate()
			return _lastModified
		}
	private var _lastModified = source.lastModified

	override fun toString(): String {
		return "Function[$function]($source)"
	}
}