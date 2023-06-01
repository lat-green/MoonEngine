package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

class RequiredFilter4Builder<C1 : Component, C2 : Component, C3 : Component, C4 : Component>(
	private val origin: Filter3Builder<C1, C2, C3>,
	override val component4Class: Class<C4>,
) :
	Filter4Builder<C1, C2, C3, C4> {

	override val component1Class: Class<C1>
		get() = origin.component1Class
	override val component2Class: Class<C2>
		get() = origin.component2Class
	override val component3Class: Class<C3>
		get() = origin.component3Class

	override fun build(): Iterable<Component4Entity<C1, C2, C3, C4>> {
		return Filter()
	}

	private inner class Filter : Iterable<Component4Entity<C1, C2, C3, C4>> {

		override fun iterator(): Iterator<Component4Entity<C1, C2, C3, C4>> {
			return origin.build().filter { component4Class in it }
				.map { ProxyComponent4Entity(it, component1Class, component2Class, component3Class, component4Class) }
				.iterator()
		}
	}

	override fun isRequired(componentClass: Class<out Component>): Boolean {
		return component4Class === componentClass || origin.isRequired(componentClass)
	}

	override fun isIgnore(componentClass: Class<out Component>): Boolean {
		return origin.isIgnore(componentClass)
	}
}
