package com.greentree.engine.moon.assets.component

object StringToIntAssetComponentLoader : AssetComponentLoader<StringToIntKey> {

	override fun load(ctx: AssetComponentContext, key: StringToIntKey) = Integer.parseInt(ctx.load(key.text))
}

class StringToIntKey(val text: AssetComponentKey<String>) : AssetComponentKey<Int>
