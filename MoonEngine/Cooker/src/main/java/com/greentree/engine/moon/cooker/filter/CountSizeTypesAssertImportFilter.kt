package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo

class CountSizeTypesAssertImportFilter(private val count: MutableMap<String, Long>) : AssetImportFilter {

	override fun doFilter(chain: AssetImportFilter.Chain, asset: AssetInfo): ImportAssetInfo? {
		val result = chain.doFilter(asset)
		if(result != null) {
			val type = asset.fileType
			count[type] = (count[type] ?: 0) + asset.size
		}
		return result
	}

	private val AssetInfo.size
		get() = open().use { input ->
			input.readAllBytes().size
		}
}
