package com.greentree.engine.moon.ecs.filter

import com.greentree.engine.moon.ecs.component.Component

interface FilterBuilder {

	fun <C : Component> required(componentClass: Class<C>): Filter1Builder<C>
}