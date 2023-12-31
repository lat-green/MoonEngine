package com.greentree.engine.moon.assets.bundle.deserializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.bundle.manager.DeserializeAssetBundleManager
import java.io.InputStream

interface TypedAssetDeserealizer<T : Any> {

	val type: TypeInfo<T>
		get() = TypeUtil.getFirstAtgument(javaClass, TypedAssetDeserealizer::class.java)

	fun deserialize(context: AssetDeserealizer.Context, stream: InputStream): T
}
