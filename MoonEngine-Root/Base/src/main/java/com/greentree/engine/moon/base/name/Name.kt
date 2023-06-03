package com.greentree.engine.moon.base.name

import com.greentree.engine.moon.ecs.component.ConstComponent
import java.util.*

data class Name(val value: String) : ConstComponent {

	fun value() = value

	override fun toString(): String {
		return "Name [$value]"
	}

	init {
		Objects.requireNonNull(value)
	}
}