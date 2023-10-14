package com.greentree.engine.moon.assets.smart

import com.greentree.engine.moon.assets.smart.info.ValidAssetInfo

class ConstSmartAsset<T : Any>(private val value: T) : SmartAsset<T> {

	override fun info(context: SmartAsset.Context) = ValidAssetInfo(value)
	override fun openConnection() = SmartAsset.Connection.Empty
}