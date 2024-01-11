package com.greentree.engine.moon.cooker.info

data class TextImportAssetInfo @JvmOverloads constructor(
	val text: String,
	override val isPrimary: Boolean = false,
	override val dependencies: Iterable<String> = listOf(),
) : ImportAssetInfo {

	override fun open() = text.byteInputStream()
}
