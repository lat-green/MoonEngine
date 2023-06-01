package com.greentree.engine.moon.ecs.system

import com.greentree.engine.moon.ecs.World

interface InitSystem : ECSSystem {

	fun init(world: World?)
}