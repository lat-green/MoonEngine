package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator

class OneSerializator(private val serializator: AssetSerializator<*>) : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T>? {
		if(TypeUtil.isExtends(type, serializator.type)) {
			return (serializator as AssetSerializator<T>).load(context, key)
		}
		return null
	}

	override fun toString(): String {
		return "OneSerializator[$serializator]"
	}
}
