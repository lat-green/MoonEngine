package com.greentree.engine.moon.assets.smart.info

import com.greentree.engine.moon.assets.smart.AssetStatus

interface AssetInfo<T : Any> {

	val value: T
	val status: AssetStatus
}

class ValidAssetInfo<T : Any>(override val value: T) : AssetInfo<T> {

	override val status: AssetStatus
		get() = AssetStatus.VALID
}