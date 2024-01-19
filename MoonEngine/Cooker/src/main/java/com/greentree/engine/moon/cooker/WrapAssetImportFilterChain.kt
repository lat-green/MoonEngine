package com.greentree.engine.moon.cooker

import com.greentree.engine.moon.cooker.filter.AssetImportFilter
import com.greentree.engine.moon.cooker.info.AssetInfo

class WrapAssetImportFilterChain(
	private val filter: AssetImportFilter,
	private val chain: AssetImportFilter.ChainAndChainIsPrimary,
) : AssetImportFilter.ChainAndChainIsPrimary {

	override fun doFilter(asset: AssetInfo) = filter.doFilter(chain, asset)

	override fun isPrimary(asset: AssetInfo) = filter.isPrimary(chain, asset)
}
