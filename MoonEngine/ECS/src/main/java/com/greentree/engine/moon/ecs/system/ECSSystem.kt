package com.greentree.engine.moon.ecs.system

import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.scene.SceneProperties
import com.greentree.engine.moon.ecs.scene.WorldProperty

sealed interface ECSSystem

interface UpdateSystem : ECSSystem {

	fun update()

	object Empty : UpdateSystem {

		override fun update() {
		}
	}

	companion object {

		fun castOrEmpty(system: ECSSystem): UpdateSystem {
			if(system is UpdateSystem)
				return system
			return Empty
		}
	}
}

interface DestroySystem : ECSSystem {

	fun destroy()

	object Empty : DestroySystem {

		override fun destroy() {
		}
	}

	companion object {

		fun castOrEmpty(system: ECSSystem): DestroySystem {
			if(system is DestroySystem)
				return system
			return Empty
		}
	}
}

interface InitSystem : ECSSystem {

	fun init(properties: SceneProperties)

	object Empty : InitSystem {

		override fun init(properties: SceneProperties) {
		}
	}

	companion object {

		fun castOrEmpty(system: ECSSystem): InitSystem {
			if(system is InitSystem)
				return system
			return Empty
		}
	}
}

interface SimpleWorldInitSystem : WorldInitSystem {

	override fun init(world: World, properties: SceneProperties) {
		return init(world)
	}

	fun init(world: World)
}

interface WorldInitSystem : InitSystem {

	override fun init(properties: SceneProperties) {
		val world = properties[WorldProperty::class.java].world
		return init(world, properties)
	}

	fun init(world: World, properties: SceneProperties)
}

interface FullSystem : InitSystem, UpdateSystem, DestroySystem

data class MergeSystem(
	private val initSystem: InitSystem,
	private val updateSystem: UpdateSystem,
	private val destroySystem: DestroySystem,
) : FullSystem, InitSystem by initSystem, UpdateSystem by updateSystem, DestroySystem by destroySystem {

	constructor(system: ECSSystem) : this(
		InitSystem.castOrEmpty(system),
		UpdateSystem.castOrEmpty(system),
		DestroySystem.castOrEmpty(system)
	)
}

fun FullSystem.toFull(): FullSystem {
	return this
}

fun ECSSystem.toFull(): FullSystem {
	if(this is FullSystem)
		return this
	return MergeSystem(this)
}