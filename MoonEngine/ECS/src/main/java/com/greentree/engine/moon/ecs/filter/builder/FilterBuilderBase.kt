package com.greentree.engine.moon.ecs.filter.builder

import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.ecs.filter.Filter
import com.greentree.engine.moon.ecs.filter.builder.world.WorldFilterBuilderBase

interface FilterBuilderBase {

	fun build(world: World): Filter<out WorldEntity> {
		return setWorld(world).build()
	}

	fun setWorld(world: World): WorldFilterBuilderBase

	fun ignore(ignoredClass: Class<out Component>): FilterBuilderBase {
		return FilterBuilder().ignore(ignoredClass)
	}

	fun <C : Component> require(requiredClass: Class<out C>): FilterBuilderBase {
		return FilterBuilder().require(requiredClass)
	}

	fun isRequired(componentClass: Class<out Component>): Boolean
	fun isIgnored(componentClass: Class<out Component>): Boolean
}