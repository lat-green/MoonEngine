package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

interface Filter2Builder<C1 : Component, C2 : Component> : FilterBuilderBase<Filter2Builder<C1, C2>> {

	val component1Class: Class<C1>
	val component2Class: Class<C2>

	override fun build(): Iterable<Component2Entity<C1, C2>>

	override fun ignore(ignoreClass: Class<out Component>): Filter2Builder<C1, C2> {
		return IgnoreFilter2Builder(this, ignoreClass)
	}

	fun <C3 : Component> required(component3Class: Class<C3>): Filter3Builder<C1, C2, C3> {
		return RequiredFilter3Builder(this, component3Class)
	}

	fun <C3 : Component, C4 : Component> required(
		component3Class: Class<C3>,
		component4Class: Class<C4>,
	): Filter4Builder<C1, C2, C3, C4> {
		return required(component3Class).required(component4Class)
	}
}
