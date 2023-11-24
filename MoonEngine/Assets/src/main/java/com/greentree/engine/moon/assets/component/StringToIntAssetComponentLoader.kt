package com.greentree.engine.moon.assets.component

object StringToIntAssetComponentLoader : MapAssetComponentLoader<String, Int> {

	override fun load(source: String) = source.toInt()
}

