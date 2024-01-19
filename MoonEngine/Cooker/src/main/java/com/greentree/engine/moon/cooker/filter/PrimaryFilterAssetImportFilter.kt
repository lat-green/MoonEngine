package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo
import com.greentree.engine.moon.cooker.info.PrimaryImportAssetInfoProxy

class PrimaryFilterAssetImportFilter(
	private val filter: (AssetInfo) -> Boolean,
) : AssetImportFilter {

	override fun doFilter(chain: AssetImportFilter.Chain, asset: AssetInfo): ImportAssetInfo? {
		val result = chain.doFilter(asset) ?: return null
		if(filter(asset))
			return PrimaryImportAssetInfoProxy(result)
		return result
	}
}