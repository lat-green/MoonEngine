package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.asset.AssetCharacteristics
import java.lang.Long.*

@Deprecated("lastModified unsupported update origin.value.lastModified")
class FlattenAssetProvider<T : Any>(
	private val origin: AssetProvider<AssetProvider<T>>,
) : AssetProvider<T>, AssetCharacteristics by origin {

	private var sourceLastUpdate = 0L
	override val value: T
		get() = origin.value.value

	override fun value(ctx: AssetContext): T {
		val source = origin.value(ctx)
		sourceLastUpdate = max(sourceLastUpdate, source.lastModified)
		return source.value(ctx)
	}

	override val lastModified
		get() = max(origin.lastModified, sourceLastUpdate)
}