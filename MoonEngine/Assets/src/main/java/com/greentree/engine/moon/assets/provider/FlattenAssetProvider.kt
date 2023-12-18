package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.request.AssetRequest

@Deprecated("lastModified unsupported update origin.value.lastModified")
class FlattenAssetProvider<T : Any>(
	private val origin: AssetProvider<AssetProvider<T>>,
) : AssetProvider<T>, AssetProviderCharacteristics by origin {

	override fun value(ctx: AssetRequest) = origin.value(ctx).value(ctx)
}