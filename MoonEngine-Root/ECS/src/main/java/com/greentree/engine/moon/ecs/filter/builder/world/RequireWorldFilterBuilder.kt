package com.greentree.engine.moon.ecs.filter.builder.world

import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.ecs.filter.Filter

class RequireWorldFilterBuilder<C : Component>(
	private val origin: WorldFilterBuilderBase,
	private val component1Class: Class<C>,
) : WorldFilterBuilderBase {

	override fun build(): Filter<out WorldEntity> {
		return origin.build().filter { component1Class in it }
	}

	override fun isRequired(componentClass: Class<out Component>): Boolean {
		return component1Class === componentClass || origin.isRequired(componentClass)
	}

	override fun isIgnored(componentClass: Class<out Component>) = origin.isIgnored(componentClass)
}
