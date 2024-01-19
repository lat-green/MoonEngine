package com.greentree.engine.moon.cooker.info

data class AddDependenciesImportAssetInfoProxy(
	val origin: ImportAssetInfo,
	val addedDependencies: Iterable<String>,
) : ImportAssetInfo by origin {

	override val dependencies: Iterable<String>
		get() = run {
			val result = mutableListOf<String>()
			result.addAll(origin.dependencies)
			result.addAll(addedDependencies)
			result
		}
}