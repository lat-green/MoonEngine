package com.greentree.engine.moon.assets.component

object SplitStringAssetComponentLoader : AssetComponentLoader<SplitStringKey> {

	override fun load(ctx: AssetComponentContext, key: SplitStringKey) = ctx.load(key.text).split(" ")
}

class SplitStringKey(val text: AssetComponentKey<String>) : AssetComponentKey<Iterable<String>>
