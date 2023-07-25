package com.greentree.engine.moon.assets.asset

import java.io.Serializable

class MapAsset<T : Any, R : Any, F> private constructor(
	asset: Asset<T>,
	override val function: F,
) :
	AbstractMapAsset<T, R>(asset) where F : (T) -> R, F : Serializable {

	companion object {

		fun <T : Any, R : Any, F> newAsset(
			asset: Asset<T>,
			function: F,
		): Asset<R> where F : (T) -> R, F : Serializable {
			if(asset.isConst())
				return ConstAsset(function(asset.value))
			return MapAsset(asset, function)
		}
	}
}