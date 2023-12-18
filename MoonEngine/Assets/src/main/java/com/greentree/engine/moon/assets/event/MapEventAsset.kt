package com.greentree.engine.moon.assets.event

import com.greentree.commons.action.observable.RunObservable
import com.greentree.engine.moon.assets.provider.AssetFunction1
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.LastValue

class MapEventAsset<T : Any, R : Any>(
	val source: EventAsset<T>,
	val function: AssetFunction1<T, R>,
) : EventAsset<R> {

	override val onChange: RunObservable
		get() = source.onChange

	override fun value(request: AssetRequest): Result<R> {
		val source = source.value(request.minusKey(LastValue))
		return source.mapCatching { function(request, it) }
	}
}
