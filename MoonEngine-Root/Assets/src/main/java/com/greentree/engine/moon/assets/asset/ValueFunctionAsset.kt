package com.greentree.engine.moon.assets.asset

import org.apache.logging.log4j.LogManager

private val LOG = LogManager.getLogger()

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

	override fun isValid() = source.isValid()

	override fun isConst() = source.isConst()

	override var cache = function(source.value)
		private set
	override val value: R
		get() {
			tryUpdate()
			return cache
		}

	private inline fun tryUpdate() {
		if(sourceLastUpdate < source.lastModified) {
			sourceLastUpdate = source.lastModified
			try {
				cache = function(source.value)
			} catch(e: Exception) {
				LOG.warn("", e)
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
}