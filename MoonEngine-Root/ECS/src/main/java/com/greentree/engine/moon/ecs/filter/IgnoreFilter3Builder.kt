package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

class IgnoreFilter3Builder<C1 : Component, C2 : Component, C3 : Component>(
	private val origin: Filter3Builder<C1, C2, C3>,
	private val ignoreClass: Class<out Component>,
) : Filter3Builder<C1, C2, C3> by origin {

	override fun build(): Iterable<Component3Entity<C1, C2, C3>> {
		require(origin.isRequired(ignoreClass)) { "$ignoreClass repeated required and ignore" }
		return origin.build().filter { ignoreClass !in it }
	}

	override fun <C4 : Component> required(component4Class: Class<C4>): Filter4Builder<C1, C2, C3, C4> {
		return origin.required(component4Class).ignore(ignoreClass)
	}

	override fun isIgnore(componentClass: Class<out Component>): Boolean {
		return ignoreClass === componentClass || origin.isIgnore(componentClass)
	}
}
