package com.greentree.engine.moon.assets.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import kotlin.reflect.KClass

interface AssetManager : AssetLoader.Context {

}


