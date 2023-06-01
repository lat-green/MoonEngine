package com.greentree.engine.moon.ecs.component

interface ConstComponent : Component {

	override fun copy(): ConstComponent {
		return this
	}

	override fun copyTo(other: Component): Boolean {
		return equals(other)
	}
}