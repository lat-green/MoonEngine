package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.LastValue

class FunctionAssetProvider<T : Any, R : Any>(
	private val source: AssetProvider<T>,
	private val function: AssetFunction1<T, R>,
) : AssetProvider<R>, AssetProviderCharacteristics by source {

	override fun value(ctx: AssetRequest): R {
		val source = source.value(ctx.minusKey(LastValue))
		return function(ctx, source)
	}
}