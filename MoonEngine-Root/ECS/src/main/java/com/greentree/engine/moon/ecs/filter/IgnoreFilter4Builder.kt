package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

class IgnoreFilter4Builder<C1 : Component, C2 : Component, C3 : Component, C4 : Component>(
	private val origin: Filter4Builder<C1, C2, C3, C4>,
	private val ignoreClass: Class<out Component>,
) : Filter4Builder<C1, C2, C3, C4> by origin {

	override fun build(): Iterable<Component4Entity<C1, C2, C3, C4>> {
		require(origin.isRequired(ignoreClass)) { "$ignoreClass repeated required and ignore" }
		return origin.build().filter { ignoreClass !in it }
	}

	override fun isIgnore(componentClass: Class<out Component>): Boolean {
		return ignoreClass === componentClass || origin.isIgnore(componentClass)
	}
}
