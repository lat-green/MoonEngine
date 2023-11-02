package com.greentree.engine.moon.base.scene.base

import com.greentree.commons.util.time.PointTimer
import com.greentree.engine.moon.base.scene.SceneManager
import com.greentree.engine.moon.ecs.CollectionWorld
import com.greentree.engine.moon.ecs.scene.MapSceneProperties
import com.greentree.engine.moon.ecs.scene.ProxySceneProperties
import com.greentree.engine.moon.ecs.scene.Scene
import com.greentree.engine.moon.ecs.scene.WorldProperty
import com.greentree.engine.moon.ecs.system.FullSystem
import com.greentree.engine.moon.ecs.system.toFull
import com.greentree.engine.moon.modules.property.EngineProperties
import org.apache.logging.log4j.LogManager

class SceneManagerBase(private val properties: EngineProperties) : SceneManager {

	companion object {

		private val LOG = LogManager.getLogger(SceneManagerBase::class.java)
	}

	private var nextScene: Scene? = null
	private lateinit var currentSystems: FullSystem

	@Synchronized
	override fun set(scene: Scene) {
		nextScene = scene
	}

	@Synchronized
	fun update() {
		nextScene?.let {
			nextScene = null
			loadScene(it)
		}
		currentSystems.update()
	}

	@Synchronized
	private fun loadScene(nextScene: Scene) {
		clearScene()
		val sceneProperties = ProxySceneProperties(properties, MapSceneProperties())
		val currentWorld = CollectionWorld()
		sceneProperties.add(WorldProperty(currentWorld))
		val currentSystems: FullSystem
		val timer = PointTimer()
		run {
			timer.point()
			currentSystems = nextScene.getSystem().toFull()
			this.currentSystems = currentSystems
			timer.point()
			LOG.info("scene load ${timer[0]}")
		}
		run {
			timer.point()
			currentSystems.init(sceneProperties)
			timer.point()
			LOG.info("scene init ${timer[2]}")
		}
	}

	@Synchronized
	fun clearScene() {
		if(::currentSystems.isInitialized)
			currentSystems.destroy()
	}
}