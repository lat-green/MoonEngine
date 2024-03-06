package com.greentree.engine.moon.base.assets.text

import com.greentree.commons.util.string.RefStringBuilder
import com.greentree.engine.moon.assets.serializator.SimpleAssetSerializator

object RefStringBuilderAssetSerializator : SimpleAssetSerializator<String, RefStringBuilder> {

	override fun invoke(value: String): RefStringBuilder = RefStringBuilder.build(value)
}