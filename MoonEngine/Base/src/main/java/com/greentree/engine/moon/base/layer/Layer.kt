package com.greentree.engine.moon.base.layer

import com.greentree.engine.moon.ecs.component.ConstComponent
import java.util.*

data class Layer(val name: String) : ConstComponent {

	fun name() = name

	override fun toString(): String {
		return "Layer [$name]"
	}

	init {
		Objects.requireNonNull(name)
	}
}