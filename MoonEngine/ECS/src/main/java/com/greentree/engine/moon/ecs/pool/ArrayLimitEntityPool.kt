package com.greentree.engine.moon.ecs.pool

import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.WorldEntity

class ArrayLimitEntityPool(
	private val world: World,
	limit: Int,
	private val strategy: EntityPoolStrategy,
) : EntityPool {

	private val pool: Array<PoolEntity> = Array(limit) { PoolEntity() }

	override fun get(): WorldEntity? {
		val result = pool.firstOrNull { it.isDeactivate() } ?: return null
		result.entity.activate()
		return result
	}

	val capacity = pool.count { it.isDeactivate() }
	fun limit() = pool.size

	private inner class PoolEntity(val entity: WorldEntity = world.newDeactivateEntity()) : WorldEntity by entity {

		override fun deactivate() = throw UnsupportedOperationException()
		override fun activate() = throw UnsupportedOperationException()

		override fun isDeleted() = false

		override fun delete() = entity.deactivate()
	}
}