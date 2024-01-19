package com.greentree.engine.moon.cooker.info

data class ImportAssetInfoImpl(
	val asset: AssetInfo,
) : ImportAssetInfo {

	override val dependencies: Iterable<String>
		get() = listOf()
	override val isPrimary: Boolean
		get() = false
	
	override fun open() = asset.open()
}
