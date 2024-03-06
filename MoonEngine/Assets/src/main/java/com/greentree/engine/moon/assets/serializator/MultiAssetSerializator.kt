package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.util.exception.MultiException
import com.greentree.commons.util.iterator.IteratorUtil.*
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import org.apache.logging.log4j.LogManager

class MultiAssetSerializator<T : Any>(private val serializators: Iterable<AssetSerializator<out T>>) :
	AssetSerializator<T> {

	override val type
		get() = TODO()

	override fun load(context: AssetLoader.Context, key: AssetKey): T {
		val exceptions = ArrayList<Throwable>()
		for(serializator in serializators) try {
			return serializator.load(context, key)
		} catch(e: RuntimeException) {
			exceptions.add(e)
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

private fun <T> Iterable<T>.isNotEmpty() = !isEmpty()
private fun <T> Iterable<T>.isEmpty() = isEmpty(this)
private val <T> Iterable<T>.size
	get() = size(this)