package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

interface Filter3Builder<C1 : Component, C2 : Component, C3 : Component> :
	FilterBuilderBase<Filter3Builder<C1, C2, C3>> {

	val component1Class: Class<C1>
	val component2Class: Class<C2>
	val component3Class: Class<C3>

	override fun ignore(ignoreClass: Class<out Component>): Filter3Builder<C1, C2, C3> {
		return IgnoreFilter3Builder(this, ignoreClass)
	}

	override fun build(): Iterable<Component3Entity<C1, C2, C3>>

	fun <C4 : Component> required(component4Class: Class<C4>): Filter4Builder<C1, C2, C3, C4> {
		return RequiredFilter4Builder(this, component4Class)
	}
}
