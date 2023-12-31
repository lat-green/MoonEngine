package com.greentree.engine.moon.assets.bundle.deserializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.NotSupportedType
import com.greentree.engine.moon.assets.bundle.manager.DeserializeAssetBundleManager
import java.io.InputStream

class TypedAssetDeserealizerWraper(
	val origin: TypedAssetDeserealizer<*>,
) : AssetDeserealizer {

	override fun <T : Any> deserialize(type: TypeInfo<T>, stream: InputStream, context: AssetDeserealizer.Context): T {
		if(TypeUtil.isExtends(type, origin.type))
			return origin.deserialize(context, stream) as T
		throw NotSupportedType
	}
}

fun DeserializeAssetBundleManager.addDeserializer(deserializer: TypedAssetDeserealizer<*>) = addDeserializer(
	TypedAssetDeserealizerWraper(deserializer)
)