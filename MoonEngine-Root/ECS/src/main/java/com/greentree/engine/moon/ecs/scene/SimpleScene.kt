package com.greentree.engine.moon.ecs.scene

import com.greentree.engine.moon.ecs.system.ECSSystem
import com.greentree.engine.moon.ecs.system.FullSystem
import com.greentree.engine.moon.ecs.system.toFull

interface SimpleScene : Scene {

	override fun getSystem(globalSystems: Iterable<out ECSSystem>): ECSSystem {
		val systems = getSystems(globalSystems).toFull()
		val system = object : FullSystem by systems {
			override fun init(properties: SceneProperties) {
				build(properties)
				systems.init(properties)
			}
		}
		return system
	}

	fun build(properties: SceneProperties)

	fun getSystems(globalSystems: Iterable<out ECSSystem>): ECSSystem
}