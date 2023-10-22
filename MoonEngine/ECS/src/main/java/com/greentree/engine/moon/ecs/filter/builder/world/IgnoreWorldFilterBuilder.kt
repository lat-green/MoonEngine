package com.greentree.engine.moon.ecs.filter.builder.world

import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.ecs.filter.Filter

class IgnoreWorldFilterBuilder(
	private val origin: WorldFilterBuilderBase,
	private val ignoredClass: Class<out Component>,
) : WorldFilterBuilderBase {

	override fun build(): Filter<out WorldEntity> {
		return origin.build().filter { ignoredClass !in it }
	}

	override fun isIgnored(componentClass: Class<out Component>): Boolean {
		return ignoredClass === componentClass || origin.isIgnored(componentClass)
	}

	override fun isRequired(componentClass: Class<out Component>) = origin.isRequired(componentClass)
}
