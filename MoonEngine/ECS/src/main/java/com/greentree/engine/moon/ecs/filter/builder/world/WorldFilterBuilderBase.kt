package com.greentree.engine.moon.ecs.filter.builder.world

import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.ecs.filter.AllEntityFilter
import com.greentree.engine.moon.ecs.filter.Filter

interface WorldFilterBuilderBase {

	fun build(): Filter<out WorldEntity>

	fun ignore(ignoredClass: Class<out Component>): WorldFilterBuilderBase {
		return IgnoreWorldFilterBuilder(this, ignoredClass)
	}

	fun <C : Component> require(requiredClass: Class<out C>): WorldFilterBuilderBase {
		return RequireWorldFilterBuilder(this, requiredClass)
	}

	fun isRequired(componentClass: Class<out Component>): Boolean
	fun isIgnored(componentClass: Class<out Component>): Boolean

	companion object {

		inline fun all(world: World) = AllWorldFilterBuilderBase(world)
	}
}

class AllWorldFilterBuilderBase(val world: World) : WorldFilterBuilderBase {

	override fun build(): Filter<out WorldEntity> {
		return AllEntityFilter(world)
	}

	override fun isRequired(componentClass: Class<out Component>) = false
	override fun isIgnored(componentClass: Class<out Component>) = false
}