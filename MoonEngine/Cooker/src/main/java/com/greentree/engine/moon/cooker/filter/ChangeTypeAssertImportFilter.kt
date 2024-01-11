package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo
import com.greentree.engine.moon.cooker.info.NewTypeAssetInfoProxy

data class ChangeTypeAssertImportFilter(val source: String, val dest: String) : AssetImportFilter {

	override fun doFilter(chain: AssetImportFilter.Chain, asset: AssetInfo): ImportAssetInfo? {
		val type = asset.fileType
		if(source == type) return chain.doFilter(NewTypeAssetInfoProxy(asset, dest))
		return chain.doFilter(asset)
	}
}
