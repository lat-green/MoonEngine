package com.greentree.engine.moon.ecs.system

import com.greentree.engine.moon.ecs.scene.SceneProperties

open class Systems(
	initSystems: List<InitSystem>, updateSystems: List<UpdateSystem>,
	destroySystems: List<DestroySystem>,
) : InitSystem, UpdateSystem, DestroySystem {

	private val initSystems: List<InitSystem>
	private val updateSystems: List<UpdateSystem>
	private val destroySystems: List<DestroySystem>

	constructor(systems: List<out ECSSystem>) : this(
		systems.filterIsInstance<InitSystem>(),
		systems.filterIsInstance<UpdateSystem>(),
		systems.filterIsInstance<DestroySystem>()
	)

	init {
		this.initSystems = ArrayList(initSystems)
		this.updateSystems = ArrayList(updateSystems)
		this.destroySystems = ArrayList(destroySystems)
	}

	override fun destroy() {
		val iter = destroySystems.iterator()
		while(iter.hasNext()) iter.next().destroy()
	}

	fun destroySystems(): Iterable<DestroySystem> {
		return destroySystems
	}

	override fun init(properties: SceneProperties) {
		val iter = initSystems.iterator()
		while(iter.hasNext()) {
			val s = iter.next()
			s.init(properties)
		}
	}

	fun initSystems(): Iterable<InitSystem> {
		return initSystems
	}

	override fun update() {
		val iter = updateSystems.iterator()
		while(iter.hasNext()) iter.next().update()
	}

	fun updateSystems(): Iterable<UpdateSystem> {
		return updateSystems
	}
}