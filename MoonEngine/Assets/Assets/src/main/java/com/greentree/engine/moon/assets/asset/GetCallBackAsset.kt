package com.greentree.engine.moon.assets.asset

data class GetCallBackAsset<T : Any>(
	val asset: Asset<T>,
	val block: (T) -> Unit,
) : Asset<T> {

	override val value: T
		get() {
			val result = asset.value
			block(result)
			return result
		}
}
