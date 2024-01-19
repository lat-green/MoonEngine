package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo

class PrimaryFilterAssetImportFilter(
	private val filter: (AssetInfo) -> Boolean,
) : AssetImportFilter {

	override fun isPrimary(chain: AssetImportFilter.ChainIsPrimary, asset: AssetInfo): Boolean {
		return filter(asset) || chain.isPrimary(asset)
	}
}