package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.context.AssetContext
import java.lang.Long.*

class ReduceAssetProvider<T : Any>(origin: AssetProvider<T>) : AssetProvider<T> {

	var origin: AssetProvider<T> = origin
		set(value) {
			sourceLastUpdate = max(sourceLastUpdate, System.currentTimeMillis())
			field = value
		}
	private var sourceLastUpdate = 0L

	override fun value(ctx: AssetContext) = origin.value(ctx)

	override val lastModified
		get() = max(sourceLastUpdate, origin.lastModified)

	override fun isValid(ctx: AssetContext) = origin.isValid(ctx)
}