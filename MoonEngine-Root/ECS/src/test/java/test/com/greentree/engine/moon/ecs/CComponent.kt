package test.com.greentree.engine.moon.ecs

import com.greentree.engine.moon.ecs.component.Component

data class CComponent(@JvmField var value: Int = 0) : Component {

	override fun copy(): CComponent {
		return CComponent(value)
	}

	override fun copyTo(other: Component): Boolean {
		if (other is CComponent) {
			other.value = value
			return true
		}
		return false
	}
}