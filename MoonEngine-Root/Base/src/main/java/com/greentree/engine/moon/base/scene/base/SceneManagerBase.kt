package com.greentree.engine.moon.base.scene.base

import com.greentree.commons.util.time.PointTimer
import com.greentree.engine.moon.base.scene.SceneManager
import com.greentree.engine.moon.ecs.CollectionWorld
import com.greentree.engine.moon.ecs.scene.MapSceneProperties
import com.greentree.engine.moon.ecs.scene.ProxySceneProperties
import com.greentree.engine.moon.ecs.scene.Scene
import com.greentree.engine.moon.ecs.scene.WorldProperty
import com.greentree.engine.moon.ecs.system.ECSSystem
import com.greentree.engine.moon.ecs.system.FullSystem
import com.greentree.engine.moon.ecs.system.toFull
import com.greentree.engine.moon.modules.property.EngineProperties
import org.apache.logging.log4j.LogManager
import java.util.*

class SceneManagerBase(private val properties: EngineProperties) : SceneManager {

	companion object {

		private val LOG = LogManager.getLogger(SceneManagerBase::class.java)
	}

	private val globalSystems: MutableCollection<ECSSystem> = ArrayList()
	private var nextScene: Scene? = null
	private var currentSystems: FullSystem? = null

	init {
		val autoAddGlobalSystems = ServiceLoader.load(ECSSystem::class.java)
		for(system in autoAddGlobalSystems) globalSystems.add(system)
	}

	@Synchronized
	override fun set(scene: Scene) {
		nextScene = scene
	}

	override fun addGlobalSystem(system: ECSSystem) {
		globalSystems.add(system)
	}

	fun update() {
		if(nextScene != null) loadScene()
		currentSystems!!.update()
	}

	@Synchronized
	private fun loadScene() {
		clearScene()
		val sceneProperties = ProxySceneProperties(properties, MapSceneProperties())
		val currentWorld = CollectionWorld()
		sceneProperties.add(WorldProperty(currentWorld))
		run {
			val timer = PointTimer()
			timer.point()
			currentSystems = nextScene!!.getSystem(globalSystems).toFull()
			timer.point()
			LOG.info("scene load ${timer[0]}")
		}
		nextScene = null
		run {
			val timer = PointTimer()
			timer.point()
			currentSystems!!.init(sceneProperties)
			timer.point()
			LOG.info("scene init ${timer[0]}")
		}
	}

	fun clearScene() {
		currentSystems?.destroy()
	}
}