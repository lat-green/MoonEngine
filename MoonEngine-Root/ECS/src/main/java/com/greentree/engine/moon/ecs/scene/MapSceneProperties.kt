package com.greentree.engine.moon.ecs.scene

import com.greentree.engine.moon.kernel.MapClassProperties
import com.greentree.engine.moon.modules.property.EngineProperty
import java.util.*

class MapSceneProperties : SceneProperties {

	private val store = MapClassProperties<EngineProperty>()

	override fun add(property: SceneProperty) {
		return store.add(property)
	}

	override fun <T : SceneProperty> remove(propertyClass: Class<out T>): T {
		return store.remove(propertyClass)
	}

	override fun clear() {
		return store.clear()
	}

	override fun <T : EngineProperty> getProperty(cls: Class<out T>): Optional<T> {
		return store.getProperty(cls)
	}

	override fun iterator(): MutableIterator<EngineProperty> {
		return store.iterator()
	}
}