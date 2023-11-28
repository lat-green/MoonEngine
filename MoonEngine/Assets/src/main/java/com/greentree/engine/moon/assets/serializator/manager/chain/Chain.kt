package com.greentree.engine.moon.assets.serializator.manager.chain

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.AssetKeyType
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.DefaultLoader

interface Chain : DefaultLoader.Context, AssetLoader.Context {

	override fun <T : Any> loadDefault(type: TypeInfo<T>, key: AssetKeyType): T?

	override fun <T : Any> load(type: TypeInfo<T>, key: AssetKey): Asset<T>

	fun <T : Any> loadCache(type: TypeInfo<T>, key: AssetKey): Asset<T>?
}