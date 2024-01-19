package com.greentree.engine.moon.cooker.info

data class TextImportAssetInfo @JvmOverloads constructor(
	val text: String,
) : ImportAssetInfo {

	override val dependencies: Iterable<String>
		get() = listOf()
	override val isPrimary: Boolean
		get() = false

	override fun open() = text.byteInputStream()
}
