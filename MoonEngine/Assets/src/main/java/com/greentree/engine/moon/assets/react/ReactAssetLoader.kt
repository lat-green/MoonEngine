package com.greentree.engine.moon.assets.react

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.event.EventAsset
import com.greentree.engine.moon.assets.event.loader.EventAssetLoader
import com.greentree.engine.moon.assets.key.AssetKey

interface ReactAssetLoader {

	fun <T> load(ctx: Context, type: TypeInfo<T>, key: AssetKey): T

	interface Context : ReactContext {

		val assets: EventAssetLoader.Context
	}
}

fun <T : Any> ReactAssetLoader.Context.useLoad(type: TypeInfo<T>, key: AssetKey): Result<T> {
	val ref by useRef { assets.load(type, key) }
	useEffectWithAutoCloseable {
		ref.onChange.addListener {
			useRefresh()
		}
	}
	return ref.value()
}