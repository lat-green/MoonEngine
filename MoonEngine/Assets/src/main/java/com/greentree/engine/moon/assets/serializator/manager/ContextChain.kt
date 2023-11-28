package com.greentree.engine.moon.assets.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader

interface ContextChain {

	fun <T : Any> load(ctx: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): AssetProvider<T>
}