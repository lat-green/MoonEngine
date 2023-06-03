package com.greentree.engine.moon.base.name

import com.greentree.engine.moon.base.component.CreateSystem
import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.filter.Filter
import com.greentree.engine.moon.ecs.filter.inverse
import com.greentree.engine.moon.ecs.filter.toMap
import com.greentree.engine.moon.ecs.scene.SceneProperty

@CreateSystem(NameSystem::class)
class Names(filter: Filter<out WorldEntity>) : SceneProperty, Iterable<String?> {

	private val names: Map<String, Collection<out WorldEntity>>

	init {
		names = filter.toMap { it[Name::class.java].value() }.inverse()
	}

	override fun iterator(): Iterator<String> {
		return names.keys.iterator()
	}

	operator fun contains(name: String): Boolean {
		return names.containsKey(name)
	}

	operator fun get(name: String): WorldEntity {
		return getAll(name).first()
	}

	fun getAll(name: String): Iterable<out WorldEntity> {
		return names[name] ?: listOf()
	}
}