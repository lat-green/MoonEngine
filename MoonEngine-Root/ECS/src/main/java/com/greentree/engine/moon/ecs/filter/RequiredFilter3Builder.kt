package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

class RequiredFilter3Builder<C1 : Component, C2 : Component, C3 : Component>(
	private val origin: Filter2Builder<C1, C2>,
	override val component3Class: Class<C3>,
) :
	Filter3Builder<C1, C2, C3> {

	override val component1Class: Class<C1>
		get() = origin.component1Class
	override val component2Class: Class<C2>
		get() = origin.component2Class

	override fun build(): Iterable<Component3Entity<C1, C2, C3>> {
		return Filter()
	}

	private inner class Filter : Iterable<Component3Entity<C1, C2, C3>> {

		override fun iterator(): Iterator<Component3Entity<C1, C2, C3>> {
			return origin.build().filter { component3Class in it }
				.map { ProxyComponent3Entity(it, component1Class, component2Class, component3Class) }.iterator()
		}
	}

	override fun isRequired(componentClass: Class<out Component>): Boolean {
		return component3Class === componentClass || origin.isRequired(componentClass)
	}

	override fun isIgnore(componentClass: Class<out Component>): Boolean {
		return origin.isIgnore(componentClass)
	}
}
