package com.greentree.engine.moon.cooker.filter

import com.greentree.engine.moon.cooker.AddDependenciesImportAssetInfoProxy
import com.greentree.engine.moon.cooker.info.AssetInfo
import com.greentree.engine.moon.cooker.info.ImportAssetInfo

data class DependencyTypeAssetImportFilter(
	val filter: (AssetInfo) -> Boolean,
	val dependencyMatcher: (AssetInfo) -> Iterable<String>,
) : AssetImportFilter {

	override fun doFilter(chain: AssetImportFilter.Chain, asset: AssetInfo): ImportAssetInfo? {
		if(filter(asset)) {
			val result = chain.doFilter(asset)
			if(result != null) {
				val dependencies = dependencyMatcher(asset)
				return AddDependenciesImportAssetInfoProxy(result, dependencies)
			}
			return null
		}
		return chain.doFilter(asset)
	}
}
