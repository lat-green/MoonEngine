package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo

interface PostAssetImportFilter : AssetImportFilter {

	override fun doFilter(chain: AssetImportFilter.Chain, asset: AssetInfo): ImportAssetInfo? {
		return doPostFilter(chain.doFilter(asset) ?: return null)
	}

	fun doPostFilter(asset: ImportAssetInfo): ImportAssetInfo?
}