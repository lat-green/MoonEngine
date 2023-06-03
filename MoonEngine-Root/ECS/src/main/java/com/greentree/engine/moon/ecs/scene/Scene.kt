package com.greentree.engine.moon.ecs.scene

import com.greentree.engine.moon.ecs.system.ECSSystem

interface Scene {

	fun getSystem(globalSystems: Iterable<out ECSSystem>): ECSSystem
}