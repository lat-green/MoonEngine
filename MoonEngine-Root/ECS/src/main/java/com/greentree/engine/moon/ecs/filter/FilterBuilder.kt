package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component

@JvmDefaultWithoutCompatibility
interface FilterBuilder : FilterBuilderBase<FilterBuilder> {

	override fun build(): Iterable<WorldEntity>

	fun <C1 : Component> required(component1Class: Class<C1>): Filter1Builder<C1> {
		return RequiredFilter1Builder(this, component1Class)
	}

	override fun ignore(componentClass: Class<out Component>): FilterBuilder {
		return IgnoreFilterBuilder(this, componentClass)
	}

	fun <C1 : Component, C2 : Component> required(
		component1Class: Class<C1>,
		component2Class: Class<C2>,
	): Filter2Builder<C1, C2> {
		return required(component1Class).required(component2Class)
	}

	fun <C1 : Component, C2 : Component, C3 : Component> required(
		component1Class: Class<C1>,
		component2Class: Class<C2>,
		component3Class: Class<C3>,
	): Filter3Builder<C1, C2, C3> {
		return required(component1Class).required(component2Class).required(component3Class)
	}

	fun <C1 : Component, C2 : Component, C3 : Component, C4 : Component> required(
		component1Class: Class<C1>,
		component2Class: Class<C2>,
		component3Class: Class<C3>,
		component4Class: Class<C4>,
	): Filter4Builder<C1, C2, C3, C4> {
		return required(component1Class).required(component2Class).required(component3Class).required(component4Class)
	}
}