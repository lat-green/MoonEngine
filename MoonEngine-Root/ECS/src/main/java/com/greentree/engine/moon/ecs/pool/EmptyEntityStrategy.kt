package com.greentree.engine.moon.ecs.pool

import com.greentree.engine.moon.ecs.Entity

object EmptyEntityStrategy : EntityPoolStrategy {

	override fun comporator(): Comparator<Entity> {
		return COMPARATOR
	}

	override fun toInstance(entity: Entity) {
		entity.clear()
	}

	private val COMPARATOR = Comparator.comparing { e: Entity -> e.size() }
}