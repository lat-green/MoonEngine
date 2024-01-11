package com.greentree.engine.moon.cooker.info

data class PrimaryImportAssetInfoProxy(val asset: ImportAssetInfo) : ImportAssetInfo by asset {

	override val isPrimary
		get() = true
}