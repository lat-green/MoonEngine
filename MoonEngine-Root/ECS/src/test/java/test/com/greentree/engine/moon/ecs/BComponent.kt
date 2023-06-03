package test.com.greentree.engine.moon.ecs

import com.greentree.engine.moon.ecs.component.Component

data class BComponent(var value: Int = 0) : Component {

	override fun copy(): BComponent {
		return BComponent(value)
	}

	override fun copyTo(other: Component): Boolean {
		if (other is BComponent) {
			other.value = value
			return true
		}
		return false
	}
}