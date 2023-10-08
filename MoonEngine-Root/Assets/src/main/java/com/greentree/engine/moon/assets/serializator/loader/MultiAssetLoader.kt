package com.greentree.engine.moon.assets.serializator.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import java.util.concurrent.Executors

class MultiAssetLoader(private val loaders: Iterable<AssetLoader>) : AssetLoader {

	val EXECUTOR = Executors.newCachedThreadPool()

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): Asset<T> {
		val exceptions = mutableListOf<Exception>()
		val results = mutableListOf<Asset<T>>()
		for(loader in loaders) {
			try {
				val result =
					run {
						var ok = false
						EXECUTOR.submit {
							Thread.sleep(10000)
							if(!ok)
								System.err.println("$loader $type $key")
						}
						try {
							loader.load(context, type, key)
						} finally {
							ok = true
						}
					}
				if(result != null)
					results.add(result)
			} catch(e: Exception) {
				exceptions.add(e)
			}
		}
		for(it in results)
			if(it.isValid())
				return it
		for(it in results)
			if(!it.isConst())
				return it

		fun toMultiException(exceptions: Collection<out Throwable>): Throwable {
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