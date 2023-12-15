package com.greentree.engine.moon.assets.event.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.event.EventAsset
import com.greentree.engine.moon.assets.key.AssetKey

interface EventAssetLoader {

	fun <T> load(ctx: Context, type: TypeInfo<T>, key: AssetKey): EventAsset<T>

	interface Context {

		fun <T> load(type: TypeInfo<T>, key: AssetKey): EventAsset<T>
	}
}