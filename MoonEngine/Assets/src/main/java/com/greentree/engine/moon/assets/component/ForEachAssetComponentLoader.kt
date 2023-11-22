package com.greentree.engine.moon.assets.component

class ForEachAssetComponentLoader<E, R>(val func: (E) -> AssetComponentKey<R>) :
	AssetComponentLoader<ForEachKey<E, R>> {

	override fun load(ctx: AssetComponentContext, key: ForEachKey<E, R>) =
		ctx.load(key.elements).map { func(it) }.map { ctx.load(it) }
}

class ForEachKey<E, R>(val elements: AssetComponentKey<Iterable<E>>) : AssetComponentKey<Iterable<R>>
