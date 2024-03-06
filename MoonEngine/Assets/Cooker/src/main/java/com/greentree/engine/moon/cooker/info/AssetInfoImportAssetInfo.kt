package com.greentree.engine.moon.cooker.info

data class AssetInfoImportAssetInfo(
	val asset: AssetInfo,
) : ImportAssetInfo {

	override val dependencies: Iterable<String>
		get() = listOf()

	override fun open() = asset.open()
}
