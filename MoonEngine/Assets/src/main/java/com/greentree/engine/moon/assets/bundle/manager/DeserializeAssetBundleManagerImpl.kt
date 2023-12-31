package com.greentree.engine.moon.assets.bundle.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedName
import com.greentree.engine.moon.assets.bundle.AssetBundle
import com.greentree.engine.moon.assets.bundle.deserializator.AssetDeserealizer
import com.greentree.engine.moon.assets.bundle.readInt
import com.greentree.engine.moon.assets.bundle.readLong
import com.greentree.engine.moon.assets.bundle.readShort
import com.greentree.engine.moon.assets.bundle.readUTF
import java.io.InputStream

class DeserializeAssetBundleManagerImpl : DeserializeAssetBundleManager, AssetDeserealizer.Context {

	private val deserializers = mutableListOf<AssetDeserealizer>()
	private val bundles = mutableListOf<AssetBundle>()

	override fun load(bundle: AssetBundle) {
		bundles.add(bundle)
	}

	override fun <T : Any> load(type: TypeInfo<T>, name: String): T {
		for(bundle in bundles) {
			val stream = bundle.getStream(type, name) ?: continue
			for(deserializer in deserializers) {
				return try {
					deserializer.deserialize(type, stream, this)
				} catch(_: Exception) {
					continue
				}
			}
		}
		throw NotSupportedName
	}

	override val names: Iterable<String>
		get() = bundles.flatMap { it.names }

	override fun addDeserializer(deserializer: AssetDeserealizer) {
		deserializers.add(deserializer)
	}

	override fun <T : Any> deserialize(type: TypeInfo<T>, name: String) = load(type, name)
}

private fun <T : Any> AssetBundle.getStream(type: TypeInfo<T>, name: String): InputStream? {
	return open().use { stream ->
		val headers = stream.offsets
		val header = headers[name]
		if(header == null)
			null
		else {
			val (offset, length) = header
			stream.skipNBytes(offset)
			stream.limit(length)
		}
	}
}

private val InputStream.offsets
	get() = sequence {
		val count = readShort().toInt()
		repeat(count) {
			val name = readUTF()
			val offset = readLong()
			val length = readInt()
			yield(name to (offset to length))
		}
	}.associate { it }
private val AssetBundle.names: Iterable<String>
	get() = open().use { stream ->
		stream.offsets
	}.map { it.key }

