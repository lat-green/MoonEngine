package com.greentree.engine.moon.assets.asset

import java.lang.Long.*

class ReduceAsset<T : Any>(initAsset: Asset<T>) : Asset<T> {

	var asset: Asset<T> = initAsset
		set(value) {
			field = value
			lastSet = System.currentTimeMillis()
		}
	private var lastSet = System.currentTimeMillis()
	override val lastModified
		get() = max(asset.lastModified, lastSet)
	override val value
		get() = asset.value

	override fun isValid() = asset.isValid()
	override fun toString(): String {
		return "ReduceAsset($asset)"
	}
}
