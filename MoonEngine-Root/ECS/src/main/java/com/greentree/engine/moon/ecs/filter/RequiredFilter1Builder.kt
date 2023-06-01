package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

class RequiredFilter1Builder<C1 : Component>(
	private val origin: FilterBuilder,
	override val component1Class: Class<C1>,
) :
	Filter1Builder<C1> {

	override fun build(): Iterable<Component1Entity<C1>> {
		return Filter()
	}

	private inner class Filter : Iterable<Component1Entity<C1>> {

		override fun iterator(): Iterator<Component1Entity<C1>> {
			return origin.build().filter { component1Class in it }
				.map { ProxyComponent1Entity(it, component1Class) }.iterator()
		}
	}

	override fun isRequired(componentClass: Class<out Component>): Boolean {
		return component1Class === componentClass || origin.isRequired(componentClass)
	}

	override fun isIgnore(componentClass: Class<out Component>): Boolean {
		return origin.isIgnore(componentClass)
	}
}
