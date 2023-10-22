package com.greentree.engine.moon.ecs.scene

import com.greentree.engine.moon.ecs.system.ECSSystem
import com.greentree.engine.moon.ecs.system.FullSystem
import com.greentree.engine.moon.ecs.system.toFull

interface SimpleScene : Scene {

	override fun getSystem(): ECSSystem {
		val systems = getSystems().toFull()
		val system = object : FullSystem by systems {
			override fun init(properties: SceneProperties) {
				build(properties)
				systems.init(properties)
			}
		}
		return system
	}

	fun build(properties: SceneProperties)

	fun getSystems(): ECSSystem
}