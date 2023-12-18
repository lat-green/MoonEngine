package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.LastValue
import com.greentree.engine.moon.assets.provider.response.AssetResponse
import com.greentree.engine.moon.assets.provider.response.BaseNotValidWithException
import com.greentree.engine.moon.assets.provider.response.BaseResult
import com.greentree.engine.moon.assets.provider.response.ConstNotValidWithException
import com.greentree.engine.moon.assets.provider.response.ConstResult
import com.greentree.engine.moon.assets.provider.response.NotValid
import com.greentree.engine.moon.assets.provider.response.WithException

class FunctionAssetProvider<T : Any, R : Any>(
	private val source: AssetProvider<T>,
	private val function: AssetFunction1<T, R>,
) : AssetProvider<R>, AssetProviderCharacteristics by source {

	override fun value(ctx: AssetRequest): AssetResponse<R> {
		val source = source.value(ctx.minusKey(LastValue))
		return source.map { function(ctx, it) }
	}
}