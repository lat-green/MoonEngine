package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.asset.AssetCharacteristics

class FunctionAssetProvider<T : Any, R : Any>(
	private val source: AssetProvider<T>,
	private val function: AssetFunction1<T, R>,
) : AssetProvider<R>, AssetCharacteristics by source {

	override fun isConst() = source.isConst()
	override fun isValid() = source.isValid()

	override fun value(ctx: AssetProvider.Context) = function(ctx, source.value(ctx))

	override val lastModified: Long
		get() = source.lastModified
}