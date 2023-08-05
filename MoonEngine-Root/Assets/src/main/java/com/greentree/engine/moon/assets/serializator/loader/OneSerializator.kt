package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager

class OneSerializator(private val serializator: AssetSerializator<*>) : AssetLoader {

	override fun <T : Any> load(manager: AssetManager, type: TypeInfo<T>, key: AssetKey): Asset<T>? {
		if(TypeUtil.isExtends(type, serializator.type)) {
			return (serializator as AssetSerializator<T>).load(manager, key)
		}
		return null
	}

	override fun toString(): String {
		return "OneSerializator[$serializator]"
	}
}
