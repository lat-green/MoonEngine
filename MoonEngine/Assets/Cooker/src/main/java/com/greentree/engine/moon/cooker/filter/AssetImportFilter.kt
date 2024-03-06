package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo

interface AssetImportFilter {

	fun doFilter(chain: Chain, asset: AssetInfo) = chain.doFilter(asset)

	interface Chain {

		fun doFilter(asset: AssetInfo): ImportAssetInfo?
	}

	fun isPrimary(chain: ChainIsPrimary, asset: AssetInfo) = chain.isPrimary(asset)

	interface ChainIsPrimary {

		fun isPrimary(asset: AssetInfo): Boolean
	}

	interface ChainAndChainIsPrimary : ChainIsPrimary, Chain
}