package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

interface Filter1Builder<C1 : Component> : FilterBuilderBase<Filter1Builder<C1>> {

	val component1Class: Class<C1>

	override fun build(): Iterable<Component1Entity<C1>>

	override fun ignore(componentClass: Class<out Component>): Filter1Builder<C1> {
		return IgnoreFilter1Builder(this, componentClass)
	}

	fun <C2 : Component> required(component2Class: Class<C2>): Filter2Builder<C1, C2> {
		return RequiredFilter2Builder(this, component2Class)
	}

	fun <C2 : Component, C3 : Component> required(
		component2Class: Class<C2>,
		component3Class: Class<C3>,
	): Filter3Builder<C1, C2, C3> {
		return required(component2Class).required(component3Class)
	}

	fun <C2 : Component, C3 : Component, C4 : Component> required(
		component2Class: Class<C2>,
		component3Class: Class<C3>,
		component4Class: Class<C4>,
	): Filter4Builder<C1, C2, C3, C4> {
		return required(component2Class).required(component3Class).required(component4Class)
	}
}
