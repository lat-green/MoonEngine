package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo

class CountTypesAssertImportFilter(private val count: MutableMap<String, Int>) : AssetImportFilter {

	override fun doFilter(chain: AssetImportFilter.Chain, asset: AssetInfo): ImportAssetInfo? {
		val result = chain.doFilter(asset)
		if(result != null) {
			val type = asset.fileType
			count[type] = (count[type] ?: 0) + 1
		}
		return result
	}
}
