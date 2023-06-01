package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

interface Filter4Builder<C1 : Component, C2 : Component, C3 : Component, C4 : Component> :
	FilterBuilderBase<Filter4Builder<C1, C2, C3, C4>> {

	val component1Class: Class<C1>
	val component2Class: Class<C2>
	val component3Class: Class<C3>
	val component4Class: Class<C4>

	override fun ignore(ignoreClass: Class<out Component>): Filter4Builder<C1, C2, C3, C4> {
		return IgnoreFilter4Builder(this, ignoreClass)
	}

	override fun build(): Iterable<Component4Entity<C1, C2, C3, C4>>
}
