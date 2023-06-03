package test.com.greentree.engine.moon.ecs

import com.greentree.engine.moon.ecs.component.Component

data class AComponent(@JvmField var value: Int = 0) : Component {

	override fun copy(): AComponent {
		return AComponent(value)
	}

	override fun copyTo(other: Component): Boolean {
		if (other is AComponent) {
			other.value = value
			return true
		}
		return false
	}
}