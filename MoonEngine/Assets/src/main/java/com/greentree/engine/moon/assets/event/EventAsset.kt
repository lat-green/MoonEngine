package com.greentree.engine.moon.assets.event

import com.greentree.commons.action.observable.RunObservable
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.request.EmptyAssetRequest

interface EventAsset<T> : AutoCloseable {

	val onChange: RunObservable

	fun value(request: AssetRequest = EmptyAssetRequest): Result<T>

	override fun close() {}
}