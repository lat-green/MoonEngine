package com.greentree.engine.moon.assets.asset

import java.lang.Long.*

class ReduceAsset<T : Any>(initAsset: Asset<T>) : Asset<T> {

	var asset: Asset<T> = initAsset
		set(value) {
			lastSet = System.currentTimeMillis()
			field = value
		}
	private var lastSet = asset.lastModified
	override val lastModified
		get() = max(asset.lastModified, lastSet)
	override val value
		get() = asset.value
}
