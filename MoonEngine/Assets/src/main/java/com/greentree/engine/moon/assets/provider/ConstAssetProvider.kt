package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ConstAsset

class ConstAssetProvider<T : Any>(override val value: T) : AssetProvider<T> {

	companion object {

		fun <T : Any> newAsset(value: T?): Asset<T> = ConstAsset(value!!)
	}

	override val lastModified: Long
		get() = 0L

	override fun value(ctx: AssetContext) = value

	override fun isConst() = true

	override fun toString(): String {
		return "Const[$value]"
	}
}