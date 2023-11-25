package com.greentree.engine.moon.assets.provider

import com.greentree.engine.moon.assets.provider.context.AssetContext

class DefaultAssetProvider<T : Any> private constructor(private val sources: Iterable<AssetProvider<T>>) :
	AssetProvider<T> {

	companion object {

		fun <T : Any> newAsset(sources: Iterable<AssetProvider<T>>) = newAsset(sources.toList())

		fun <T : Any> newAsset(sources: Collection<AssetProvider<T>>): AssetProvider<T> {
			val assets = sources.filter { !it.isConst() || it.isValid() }
			if(assets.size == 1)
				return assets.first()
			for(asset in assets)
				if(asset.isConst() && asset.isValid())
					return asset
				else
					break
			return DefaultAssetProvider(assets)
		}
	}

	override fun value(ctx: AssetContext): T {
		for(source in sources)
			if(source.isValid(ctx))
				return source.value(ctx)
		throw NoSuchElementException("not have valid source. sources:$sources")
	}

	override fun isValid(ctx: AssetContext) = sources.any { it.isValid(ctx) }

	override fun toString(): String {
		var sources = sources.toString()
		sources = sources.substring(1, sources.length - 1)
		return "Default(${sources})"
	}

	override val lastModified
		get() = sources.maxOf { it.lastModified }
}