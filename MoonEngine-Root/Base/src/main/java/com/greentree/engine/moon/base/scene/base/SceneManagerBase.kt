package com.greentree.engine.moon.base.scene.base

import com.greentree.engine.moon.base.scene.EnginePropertiesSceneProperty
import com.greentree.engine.moon.base.scene.SceneManager
import com.greentree.engine.moon.ecs.ArchetypeWorld
import com.greentree.engine.moon.ecs.scene.MapSceneProperties
import com.greentree.engine.moon.ecs.scene.Scene
import com.greentree.engine.moon.ecs.scene.SceneProperty
import com.greentree.engine.moon.ecs.scene.WorldProperty
import com.greentree.engine.moon.ecs.system.ECSSystem
import com.greentree.engine.moon.ecs.system.FullSystem
import com.greentree.engine.moon.ecs.system.toFull
import com.greentree.engine.moon.modules.property.EngineProperties
import java.util.*

class SceneManagerBase(private val properties: EngineProperties) : SceneManager {

	private val globalSystems: MutableCollection<ECSSystem> = ArrayList()
	private var nextScene: Scene? = null
	private var currentSystems: FullSystem? = null

	init {
		val autoAddGlobalSystems = ServiceLoader.load(ECSSystem::class.java)
		for (system in autoAddGlobalSystems) globalSystems.add(system)
	}

	@Synchronized
	override fun set(scene: Scene) {
		nextScene = scene
	}

	override fun addGlobalSystem(system: ECSSystem) {
		globalSystems.add(system)
	}

	fun update() {
		if (nextScene != null) loadScene()
		currentSystems!!.update()
	}

	@Synchronized
	private fun loadScene() {
		clearScene()
		val sceneProperties = MapSceneProperties()
		val currentWorld = ArchetypeWorld()
		sceneProperties.add(WorldProperty(currentWorld))
		sceneProperties.add(EnginePropertiesSceneProperty(properties))
		for (property in properties) if (property is SceneProperty) sceneProperties.add(property)
		currentSystems = nextScene!!.getSystem(globalSystems).toFull()
		nextScene = null
		currentSystems!!.init(sceneProperties)
	}

	fun clearScene() {
		currentSystems?.destroy()
	}
}