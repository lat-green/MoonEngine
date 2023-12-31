package com.greentree.engine.moon.assets.component

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.change.ChangeHandler
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.request.AssetRequest
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader

class ComponentAsset<T : Any>(
	val context: AssetLoader.Context,
	val block: (AssetComponentLoader.Context) -> T,
) : AssetProvider<T> {

	init {
		value()
	}

	private var sources = mutableListOf<AssetProvider<*>>()

	override fun value(ctx: AssetRequest): T {
		sources.clear()
		return block(object : AssetComponentLoader.Context {
			override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): T {
				val source = context.load(type, key)
				sources.add(source)
				return source.value(ctx)
			}
		})
	}

	override val changeHandlers: Sequence<ChangeHandler>
		get() = sources.asSequence().flatMap { it.changeHandlers }.distinct()
}
