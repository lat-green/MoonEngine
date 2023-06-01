package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

class RequiredFilter2Builder<C1 : Component, C2 : Component>(
	private val origin: Filter1Builder<C1>,
	override val component2Class: Class<C2>,
) :
	Filter2Builder<C1, C2> {

	override val component1Class: Class<C1>
		get() = origin.component1Class

	override fun build(): Iterable<Component2Entity<C1, C2>> {
		return Filter()
	}

	private inner class Filter : Iterable<Component2Entity<C1, C2>> {

		override fun iterator(): Iterator<Component2Entity<C1, C2>> {
			return origin.build().filter { component2Class in it }
				.map { ProxyComponent2Entity(it, component1Class, component2Class) }.iterator()
		}
	}

	override fun isRequired(componentClass: Class<out Component>): Boolean {
		return component2Class === componentClass || origin.isRequired(componentClass)
	}

	override fun isIgnore(componentClass: Class<out Component>): Boolean {
		return origin.isIgnore(componentClass)
	}
}
