package com.greentree.engine.moon.assets.event

import com.greentree.commons.action.observable.RunObservable
import com.greentree.engine.moon.assets.provider.request.AssetRequest

class ConstEventAsset<T>(
	val value: T,
) : EventAsset<T> {

	override val onChange: RunObservable
		get() = RunObservable.NULL

	override fun value(request: AssetRequest) = Result.success(value)
}