package com.greentree.engine.moon.assets.asset

class ValueFunctionAsset<T : Any, R : Any> private constructor(
	val asset: Asset<T>,
	val function: Value1Function<T, R>,
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

	override fun isConst() = asset.isConst()

	private var cache: R = function(asset.value)
	override val value: R
		get() {
			if(lastModified < asset.lastModified) {
				lastModified = asset.lastModified
				cache = function(asset.value, cache)
			}
			return cache
		}
	override var lastModified = asset.lastModified
		private set

	override fun toString(): String {
		return "Function[$function](${asset})"
	}
}