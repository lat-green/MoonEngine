package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.change.ChangeHandler
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.response.ConstResult

data class ConstAssetProvider<T : Any>(val value: T) : AssetProvider<T> {

	override fun value(ctx: AssetRequest) = ConstResult(value)

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequenceOf()

	override fun toString(): String {
		return "Const[$value]"
	}
}

fun <T : Any> ConstAssetProvider(value: T?) = ConstAssetProvider(value!!)