package com.greentree.engine.moon.assets.event

import com.greentree.commons.action.ListenerCloser
import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.action.observer.run.RunAction
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.LastValue
import com.greentree.engine.moon.assets.provider.request.TryNotUpdate
import com.greentree.engine.moon.assets.provider.request.contains

class CacheEventAsset<T : Any>(
	val origin: EventAsset<T>,
) : EventAsset<T> {

	private val lc: ListenerCloser
	private val action = RunAction()
	private var isChange = true
	private var cache: Result<T>? = null

	init {
		lc = origin.onChange.addListener {
			isChange = true
			action.event()
		}
	}

	override val onChange: RunObservable
		get() = action

	override fun value(request: AssetRequest): Result<T> {
		var cache = cache
		if(cache == null || (isChange && TryNotUpdate !in request)) {
			var ctx = request
			ctx = ctx.minusKey(LastValue)
			if(cache != null)
				ctx += LastValue(cache)
			cache = origin.value(ctx)
			this.cache = cache
			return cache
		}
		return cache
	}

	override fun close() {
		lc.close()
	}
}