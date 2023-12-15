package com.greentree.engine.moon.assets.react

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.event.loader.EventAssetLoader
import com.greentree.engine.moon.assets.key.AssetKey

data class ReactEventAssetLoader(
	val origin: ReactAssetLoader,
) : EventAssetLoader {

	override fun <T> load(ctx: EventAssetLoader.Context, type: TypeInfo<T>, key: AssetKey) = ReactAsset(origin, ctx, type, key)
}