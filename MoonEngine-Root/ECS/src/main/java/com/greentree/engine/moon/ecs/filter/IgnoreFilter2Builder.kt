package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

class IgnoreFilter2Builder<C1 : Component, C2 : Component>(
	private val origin: Filter2Builder<C1, C2>,
	private val ignoreClass: Class<out Component>,
) : Filter2Builder<C1, C2> by origin {

	override fun build(): Iterable<Component2Entity<C1, C2>> {
		require(origin.isRequired(ignoreClass)) { "$ignoreClass repeated required and ignore" }
		return origin.build().filter { ignoreClass !in it }
	}

	override fun <C3 : Component> required(component3Class: Class<C3>): Filter3Builder<C1, C2, C3> {
		return origin.required(component3Class).ignore(ignoreClass)
	}

	override fun isIgnore(componentClass: Class<out Component>): Boolean {
		return ignoreClass === componentClass || origin.isIgnore(componentClass)
	}
}
