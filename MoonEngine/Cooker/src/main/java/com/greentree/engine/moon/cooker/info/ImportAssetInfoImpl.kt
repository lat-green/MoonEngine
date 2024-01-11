package com.greentree.engine.moon.cooker.info

data class ImportAssetInfoImpl(
	val asset: AssetInfo,
	override val isPrimary: Boolean,
	override val dependencies: Iterable<String> = listOf(),
) : ImportAssetInfo {

	override fun open() = asset.open()
}
