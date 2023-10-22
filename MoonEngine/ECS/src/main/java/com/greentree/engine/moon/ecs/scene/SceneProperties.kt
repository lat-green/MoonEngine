package com.greentree.engine.moon.ecs.scene

interface SceneProperties : ReadOnlySceneProperties {

	fun add(property: SceneProperty)
	fun <T : SceneProperty> remove(propertyClass: Class<out T>): T
	fun clear()
}