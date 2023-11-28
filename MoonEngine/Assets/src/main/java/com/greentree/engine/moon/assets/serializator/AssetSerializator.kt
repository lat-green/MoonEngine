package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader

interface AssetSerializator<T : Any> {

	val type: TypeInfo<T>
		get() = TypeUtil.getFirstAtgument(javaClass, AssetSerializator::class.java)

	fun load(manager: AssetLoader.Context, key: AssetKey): Asset<T>?
}
