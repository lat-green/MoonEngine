package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.change.ChangeHandler
import com.greentree.engine.moon.assets.provider.request.AssetRequest

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

	override fun value(ctx: AssetRequest): T {
		for(source in sources)
			return try {
				source.value(ctx)
			} catch(e: Exception) {
				continue
			}
		throw NoSuchElementException("not have valid source. sources:$sources")
	}

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sequence {
			for(s in sources)
				yieldAll(s.changeHandlers)
		}

	override fun toString(): String {
		var sources = sources.toString()
		sources = sources.substring(1, sources.length - 1)
		return "Default(${sources})"
	}
}