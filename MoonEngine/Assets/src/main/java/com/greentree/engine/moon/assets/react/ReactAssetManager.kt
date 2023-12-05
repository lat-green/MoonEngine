package com.greentree.engine.moon.assets.react

import com.greentree.engine.moon.assets.react.loader.ReactAssetLoader
import com.greentree.engine.moon.assets.serializator.manager.AssetManager

interface ReactAssetManager : AssetManager {

	fun addLoader(loader: ReactAssetLoader)
}
