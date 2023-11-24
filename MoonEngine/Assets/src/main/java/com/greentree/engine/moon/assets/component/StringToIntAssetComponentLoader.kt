package com.greentree.engine.moon.assets.component

object StringToIntAssetComponentLoader : AssetComponentLoader<StringToIntKey, Int> {

	override fun load(ctx: AssetComponentContext, key: StringToIntKey) = ctx.load(key.text).map { Integer.parseInt(it) }
}

class StringToIntKey(val text: AssetComponentKey<String>) : AssetComponentKey<Int>
