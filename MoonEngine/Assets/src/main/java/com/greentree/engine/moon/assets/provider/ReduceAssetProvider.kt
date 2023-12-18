package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.change.ChangeHandler
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import java.lang.Long.*

class ReduceAssetProvider<T : Any>(origin: AssetProvider<T>) : AssetProvider<T>, ChangeHandler {

	var origin: AssetProvider<T> = origin
		set(value) {
			sourceLastUpdate = max(sourceLastUpdate, System.currentTimeMillis())
			field = value
		}
	private var sourceLastUpdate = 0L

	override fun value(ctx: AssetRequest) = origin.value(ctx)

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequence {
			yield(this@ReduceAssetProvider)
			yieldAll(origin.changeHandlers)
		}
	override val lastModified
		get() = sourceLastUpdate
}