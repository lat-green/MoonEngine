package com.greentree.engine.moon.assets.asset

class DefaultAsset<T : Any> private constructor(private val sources: Iterable<Asset<T>>) : Asset<T> {

	companion object {

		fun <T : Any> newAsset(sources: Iterable<Asset<T>>) = newAsset(sources.toList())

		fun <T : Any> newAsset(sources: Collection<Asset<T>>): Asset<T> {
			val assets = sources.filter { !it.isConst() || it.isValid() }
			if(assets.size == 1)
				return assets.first()
			for(asset in assets)
				if(asset.isConst() && asset.isValid())
					return asset
				else
					break
			return DefaultAsset(assets)
		}
	}

	override fun isValid() = sources.any { it.isValid() }

	override fun toString(): String {
		var sources = sources.toString()
		sources = sources.substring(1, sources.length - 1)
		return "Default(${sources})"
	}

	override val value: T
		get() {
			for(source in sources)
				if(source.isValid())
					return source.value
			throw NoSuchElementException("not have valid source. sources:$sources")
		}
	override val lastModified
		get() = sources.maxOf { it.lastModified }
}