package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.commons.util.exception.MultiException
import com.greentree.commons.util.iterator.IteratorUtil.*
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import org.apache.logging.log4j.LogManager

class MultiAssetSerializator<T : Any>(private val serializators: Iterable<AssetSerializator<out T>>) :
	AssetSerializator<T> {

	override val type: TypeInfo<T>
		get() {
			require(serializators.isNotEmpty())
			val type = TypeUtil.lca(map(serializators) { it.type })
			return type as TypeInfo<T>
		}

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<T> {
		val exceptions = ArrayList<Throwable>()
		for(serializator in serializators) {
			return try {
				serializator.load(manager, key)
			} catch(e: RuntimeException) {
				exceptions.add(e)
				continue
			} ?: continue
		}
		if(exceptions.isNotEmpty()) {
			"no one serializator can not load $key " + toString(serializators)
		}
		throw MultiException(exceptions)
	}

	override fun toString(): String {
		if(serializators.size == 1)
			return serializators.first().toString()
		return "MultiAssetSerializator " + toString(serializators)
	}

	companion object {

		private val LOG = LogManager.getLogger(MultiAssetSerializator::class.java)
	}
}

private inline fun <T> Iterable<T>.isNotEmpty() = !isEmpty()
private inline fun <T> Iterable<T>.isEmpty() = isEmpty(this)
private val <T> Iterable<T>.size
	get() = size(this)