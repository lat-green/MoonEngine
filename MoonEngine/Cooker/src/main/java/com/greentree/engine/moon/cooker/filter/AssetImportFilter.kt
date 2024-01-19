package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo

interface AssetImportFilter {

	fun doFilter(chain: Chain, asset: AssetInfo): ImportAssetInfo? = chain.doFilter(asset)

	interface Chain {

		fun doFilter(asset: AssetInfo): ImportAssetInfo?
	}
}