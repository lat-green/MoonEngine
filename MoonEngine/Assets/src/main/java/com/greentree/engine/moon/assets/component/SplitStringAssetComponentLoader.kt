package com.greentree.engine.moon.assets.component

object SplitStringAssetComponentLoader : AssetComponentLoader<SplitStringKey, List<String>> {

	override fun load(ctx: AssetComponentContext, key: SplitStringKey) = ctx.load(key.text).map { it.split(" ") }
}

class SplitStringKey(val text: AssetComponentKey<String>) : AssetComponentKey<List<String>>
