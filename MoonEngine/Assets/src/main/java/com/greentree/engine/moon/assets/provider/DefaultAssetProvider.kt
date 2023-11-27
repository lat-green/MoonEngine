package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.provider.response.AssetResponse
import com.greentree.engine.moon.assets.provider.response.BaseNotValidWithException
import com.greentree.engine.moon.assets.provider.response.ResultResponse

class DefaultAssetProvider<T : Any> private constructor(private val sources: Iterable<AssetProvider<T>>) :
	AssetProvider<T> {

	companion object {

		fun <T : Any> newAsset(sources: Iterable<AssetProvider<T>>) = newAsset(sources.toList())

		fun <T : Any> newAsset(sources: Collection<AssetProvider<T>>): AssetProvider<T> {
			if(sources.size == 1)
				return sources.first()
			return DefaultAssetProvider(sources)
		}
	}

	override fun value(ctx: AssetRequest): AssetResponse<T> {
		for(source in sources)
			when(val source = source.value(ctx)) {
				is ResultResponse -> return source
				else -> continue
			}
		return BaseNotValidWithException(NoSuchElementException("not have valid source. sources:$sources"))
	}

	override fun toString(): String {
		var sources = sources.toString()
		sources = sources.substring(1, sources.length - 1)
		return "Default(${sources})"
	}

	override val lastModified
		get() = sources.maxOf { it.lastModified }
}