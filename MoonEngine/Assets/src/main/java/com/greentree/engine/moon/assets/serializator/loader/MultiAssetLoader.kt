package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.NotSupportedKeyAndType
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.NotSupportedType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider

class MultiAssetLoader(private val loaders: Iterable<AssetLoader>) : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): AssetProvider<T> {
		val exceptions = mutableListOf<Exception>()
		val results = mutableListOf<AssetProvider<T>>()
		for(loader in loaders) try {
			results.add(loader.load(context, type, key))
		} catch(_: NotSupportedType) {
		} catch(_: NotSupportedKeyType) {
		} catch(_: NotSupportedKeyAndType) {
		} catch(e: Exception) {
			exceptions.add(e)
		}

		if(results.isNotEmpty()) {
			if(results.size == 1)
				return results.first()
			return results.minBy { it.toString().length }
		}

		fun toMultiException(exceptions: Collection<Throwable>): Throwable {
			if(exceptions.size == 1)
				throw exceptions.first()
			val exception = NoOneLoaderCanNotLoadType(type, key, loaders)
			for(e in exceptions)
				exception.addSuppressed(e)
			throw exception
		}
//		val baseExceptions = exceptions.toOk()
//		if(baseExceptions.isNotEmpty())
//			throw toMultiException(baseExceptions)
		throw toMultiException(exceptions)
	}

	override fun toString(): String {
		return "MultiLoader($loaders)"
	}
}

private fun Iterable<Throwable>.toOk(): Collection<out Throwable> {
	return flatMap { it.toOk() }
}

private fun Throwable.toOk(): Collection<out Throwable> {
	if(this is NoOneLoaderCanNotLoadType || this is UnsupportedOperationException)
		return subThrowable.toOk()
	if(this is RuntimeException) {
		val subThrowable = subThrowable
		if(subThrowable.isNotEmpty())
			return subThrowable.toOk()
	}
	return listOf(this)
}

private val Throwable.subThrowable: Collection<out Throwable>
	get() {
		val result = mutableListOf<Throwable>()
		cause?.let { result.add(it) }
		result.addAll(suppressed)
		return result
	}