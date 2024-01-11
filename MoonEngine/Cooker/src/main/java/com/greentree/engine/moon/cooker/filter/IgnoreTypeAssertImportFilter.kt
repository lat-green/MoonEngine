package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo

class IgnoreTypeAssertImportFilter(val type: String) : AssetImportFilter {

	override fun doFilter(chain: AssetImportFilter.Chain, asset: AssetInfo): ImportAssetInfo? {
		val type = asset.fileType
		if(this.type == type) return null
		return chain.doFilter(asset)
	}
}
