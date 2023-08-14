package com.greentree.engine.moon.assets.asset

import org.apache.logging.log4j.LogManager

private val LOG = LogManager.getLogger()

class ValueFunctionAsset<T : Any, R : Any> private constructor(
	private val source: Asset<T>,
	private val function: SmartFunction<T, R>,
) : Asset<R> {

	companion object {

		fun <T : Any, R : Any> newAsset(
			asset: Asset<T>,
			function: SmartFunction<T, R>,
		): Asset<R> {
			if(asset.isConst())
				return ConstAsset(function(asset.value).result.result)
			return ValueFunctionAsset(asset, function)
		}
	}

	override fun isValid() = source.isValid()

	override fun isConst() = source.isConst()

	override var cache = function(source.value).result.result
		private set
	override val value: R
		get() {
			tryUpdate()
			return cache
		}

	private inline fun tryUpdate() {
		if(_lastModified < source.lastModified) {
			when(val result = function(source.value).result) {
				is ValueResult<R> -> {
					_lastModified = source.lastModified
					cache = result.result
				}

				is RepeatResult<R> -> {
				}

				is ErrorResult<R> -> {
					_lastModified = source.lastModified
					LOG.warn("", result.exception)
				}
			}
		}
	}

	override val lastModified: Long
		get() {
			tryUpdate()
			return _lastModified
		}
	private var _lastModified = source.lastModified

	override fun toString(): String {
		return "Function[${function}]($source)"
	}
}