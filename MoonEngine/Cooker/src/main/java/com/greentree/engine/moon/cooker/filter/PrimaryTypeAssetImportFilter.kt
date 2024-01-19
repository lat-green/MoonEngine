package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo

class PrimaryTypeAssetImportFilter(
	private val type: String,
) : AssetImportFilter {

	override fun isPrimary(chain: AssetImportFilter.ChainIsPrimary, asset: AssetInfo): Boolean {
		return asset.fileType == type || chain.isPrimary(asset)
	}
}
