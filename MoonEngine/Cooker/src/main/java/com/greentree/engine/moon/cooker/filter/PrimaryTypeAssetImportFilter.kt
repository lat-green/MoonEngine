package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo
import com.greentree.engine.moon.cooker.info.PrimaryImportAssetInfoProxy

class PrimaryTypeAssetImportFilter(
	private val type: String,
) : AssetImportFilter {

	override fun doFilter(chain: AssetImportFilter.Chain, asset: AssetInfo): ImportAssetInfo? {
		val result = chain.doFilter(asset) ?: return null
		if(!result.isPrimary && asset.fileType == type)
			return PrimaryImportAssetInfoProxy(result)
		return result
	}
}
