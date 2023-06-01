package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component

class IgnoreFilterBuilder(private val origin: FilterBuilder, private val ignoreClass: Class<out Component>) :
	FilterBuilder by origin {

	override fun build(): Iterable<WorldEntity> {
		require(origin.isRequired(ignoreClass)) { "$ignoreClass repeated required and ignore" }
		return origin.build().filter { ignoreClass !in it }
	}

	override fun <C1 : Component> required(component1Class: Class<C1>): Filter1Builder<C1> {
		return origin.required(component1Class).ignore(ignoreClass)
	}

	override fun isIgnore(componentClass: Class<out Component>): Boolean {
		return componentClass === ignoreClass || origin.isIgnore(componentClass)
	}
}
