package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.context.AssetContext
import com.greentree.engine.moon.assets.provider.context.LastValue

class FunctionAssetProvider<T : Any, R : Any>(
	private val source: AssetProvider<T>,
	private val function: AssetFunction1<T, R>,
) : AssetProvider<R>, AssetProviderCharacteristics by source {

	override fun isConst() = source.isConst()
	override fun isValid(ctx: AssetContext) = source.isValid(ctx) && function.isValid(ctx, source.value(ctx))

	override fun value(ctx: AssetContext) = function(ctx, source.value(ctx.minusKey(LastValue)))

	override val lastModified: Long
		get() = source.lastModified
}