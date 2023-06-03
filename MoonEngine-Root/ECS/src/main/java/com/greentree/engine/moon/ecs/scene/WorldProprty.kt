package com.greentree.engine.moon.ecs.scene

import com.greentree.engine.moon.ecs.World

data class WorldProperty(val world: World) : SceneProperty {

	fun world() = world
}