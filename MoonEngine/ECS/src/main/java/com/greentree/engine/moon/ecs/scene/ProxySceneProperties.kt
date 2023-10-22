package com.greentree.engine.moon.ecs.scene

import com.greentree.engine.moon.modules.property.EngineProperty
import com.greentree.engine.moon.modules.property.ReadOnlyEngineProperties
import java.util.*

class ProxySceneProperties(
	private val engineProperties: ReadOnlyEngineProperties,
	private val sceneProperties: SceneProperties,
) : SceneProperties {

	override fun add(property: SceneProperty) {
		require(!engineProperties.contains(property::class.java)) { "already added $property" }
		return sceneProperties.add(property)
	}

	override fun <T : SceneProperty> remove(propertyClass: Class<out T>): T {
		require(engineProperties.contains(propertyClass)) { "not exists $propertyClass" }
		return sceneProperties.remove(propertyClass)
	}

	override fun clear() {
		sceneProperties.clear()
	}

	override fun <T : EngineProperty> getProperty(cls: Class<out T>): Optional<T> {
		val s = sceneProperties.getProperty(cls)
		if(s.isPresent)
			return s
		return engineProperties.getProperty(cls)
	}

	override fun iterator(): Iterator<EngineProperty> {
		return (engineProperties + sceneProperties).iterator()
	}
}