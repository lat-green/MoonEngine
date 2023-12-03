package com.greentree.engine.moon.assets.react

import com.greentree.engine.moon.assets.serializator.manager.AssetManager

interface ReactAssetManager : AssetManager {

	fun addComponent(component: ReactComponent)

}