package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.response.AssetResponse
import com.greentree.engine.moon.assets.provider.response.NotValid
import com.greentree.engine.moon.assets.provider.response.ResultResponse
import java.lang.Long.*

@Deprecated("lastModified unsupported update origin.value.lastModified")
class FlattenAssetProvider<T : Any>(
	private val origin: AssetProvider<AssetProvider<T>>,
) : AssetProvider<T>, AssetProviderCharacteristics by origin {

	private var sourceLastUpdate = 0L

	override fun value(ctx: AssetRequest): AssetResponse<T> {
		return when(val source = origin.value(ctx)) {
			is ResultResponse -> {
				val source = source.value
				sourceLastUpdate = source.lastModified
				return source.value(ctx)
			}

			is NotValid -> source
		} as AssetResponse<T>
	}

	override val lastModified
		get() = max(origin.lastModified, sourceLastUpdate)
}