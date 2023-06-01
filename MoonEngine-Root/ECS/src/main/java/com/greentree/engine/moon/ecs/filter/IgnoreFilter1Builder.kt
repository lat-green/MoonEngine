package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

class IgnoreFilter1Builder<C1 : Component>(
	private val origin: Filter1Builder<C1>,
	private val ignoreClass: Class<out Component>,
) : Filter1Builder<C1> by origin {

	override fun build(): Iterable<Component1Entity<C1>> {
		require(origin.isRequired(ignoreClass)) { "$ignoreClass repeated required and ignore" }
		return origin.build().filter { ignoreClass !in it }
	}

	override fun <C2 : Component> required(component2Class: Class<C2>): Filter2Builder<C1, C2> {
		return origin.required(component2Class).ignore(ignoreClass)
	}

	override fun isIgnore(componentClass: Class<out Component>): Boolean {
		return ignoreClass === componentClass || origin.isIgnore(componentClass)
	}
}
