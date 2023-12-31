package com.greentree.engine.moon.assets.bundle.deserializator

import com.greentree.commons.reflection.info.TypeInfo
import java.io.InputStream

interface AssetDeserealizer {

	fun <T : Any> deserialize(type: TypeInfo<T>, stream: InputStream, context: Context): T

	interface Context {

		fun <T : Any> deserialize(type: TypeInfo<T>, name: String): T
	}
}
