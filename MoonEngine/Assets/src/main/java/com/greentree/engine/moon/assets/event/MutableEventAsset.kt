package com.greentree.engine.moon.assets.event

import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.action.observer.run.RunAction
import com.greentree.engine.moon.assets.provider.request.AssetRequest

class MutableEventAsset<T>(
	value: T,
) : EventAsset<T> {

	private val action = RunAction()
	var value = value
		set(value) {
			field = value
			action.event()
		}
	override val onChange: RunObservable
		get() = action

	override fun value(request: AssetRequest) = Result.success(value)
}