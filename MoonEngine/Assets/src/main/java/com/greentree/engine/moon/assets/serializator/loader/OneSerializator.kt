package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.NotSupportedType
import com.greentree.engine.moon.assets.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.serializator.AssetSerializator

class OneSerializator(private val serializator: AssetSerializator<*>) : AssetLoader {

	private val type = serializator.type

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): AssetProvider<T> {
		if(TypeUtil.isExtends(type, this.type)) {
			return (serializator as AssetSerializator<T>).load(context, key)
		}
		throw NotSupportedType
	}

	override fun toString(): String {
		return "OneSerializator[$serializator]"
	}
}
