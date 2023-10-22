package com.greentree.engine.moon.ecs.filter.builder

import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.ecs.filter.builder.world.WorldFilterBuilderBase

class FilterBuilder : FilterBuilderBase {

	private val ignoredClasses: MutableCollection<Class<out Component>> = HashSet()
	private val requiredClasses: MutableCollection<Class<out Component>> = HashSet()

	override fun ignore(ignoredClass: Class<out Component>): FilterBuilder {
		ignoredClasses.add(ignoredClass)
		return this
	}

	override fun <C : Component> require(requiredClass: Class<out C>): FilterBuilder {
		requiredClasses.add(requiredClass)
		return this
	}

	override fun setWorld(world: World): WorldFilterBuilderBase {
		var builder = world.newFilterBuilder()
		for (requiredClass in requiredClasses)
			builder = builder.require(requiredClass)
		for (ignoredClass in ignoredClasses)
			builder = builder.ignore(ignoredClass)
		return builder
	}

	override fun isIgnored(componentClass: Class<out Component>): Boolean {
		return componentClass in ignoredClasses
	}

	override fun isRequired(componentClass: Class<out Component>): Boolean {
		return componentClass in requiredClasses
	}

	override fun toString(): String {
		return "FilterBuilder($requiredClasses, $ignoredClasses)"
	}
}