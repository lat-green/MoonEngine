package com.greentree.engine.moon.assets.asset

class ValueFunctionAsset<T : Any, R : Any> private constructor(
	asset: Asset<T>,
	override val function: Value1Function<T, R>,
) :
	AbstractMapAsset<T, R>(asset) {

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