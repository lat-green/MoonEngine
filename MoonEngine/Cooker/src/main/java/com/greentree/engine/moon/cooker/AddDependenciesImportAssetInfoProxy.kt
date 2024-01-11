package com.greentree.engine.moon.cooker

import com.greentree.engine.moon.cooker.info.ImportAssetInfo

data class AddDependenciesImportAssetInfoProxy(
	val origin: ImportAssetInfo,
	val addedDependencies: Iterable<String>,
) : ImportAssetInfo by origin {

	override val dependencies: Iterable<String>
		get() = sequence {
			yieldAll(origin.dependencies)
			yieldAll(addedDependencies)
		}.toList()
}