package com.greentree.engine.moon.cooker.info

data class NewTypeAssetInfoProxy(
	val asset: AssetInfo,
	override val fileType: String,
) : AssetInfo by asset