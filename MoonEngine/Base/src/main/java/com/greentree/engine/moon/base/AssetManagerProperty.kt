package com.greentree.engine.moon.base

import com.greentree.engine.moon.assets.manager.MutableAssetManager
import com.greentree.engine.moon.ecs.scene.SceneProperties
import com.greentree.engine.moon.ecs.scene.SceneProperty
import com.greentree.engine.moon.modules.property.EngineProperties

data class AssetManagerProperty(@JvmField val manager: MutableAssetManager) : SceneProperty

val EngineProperties.assets
	get() = get(AssetManagerProperty::class.java).manager
val SceneProperties.assets
	get() = get(AssetManagerProperty::class.java).manager