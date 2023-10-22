package com.greentree.engine.moon.ecs.system.debug

import com.greentree.engine.moon.ecs.scene.SceneProperties
import com.greentree.engine.moon.ecs.system.DestroySystem
import com.greentree.engine.moon.ecs.system.ECSSystem
import com.greentree.engine.moon.ecs.system.InitSystem
import com.greentree.engine.moon.ecs.system.Systems
import com.greentree.engine.moon.ecs.system.UpdateSystem
import com.greentree.engine.moon.kernel.use

class DebugSystems(
	private val profiler: SystemsProfiler, initSystems: List<InitSystem>,
	updateSystems: List<UpdateSystem>, destroySystems: List<DestroySystem>,
) : Systems(initSystems, updateSystems, destroySystems) {

	override fun update() {
		val iter: Iterator<UpdateSystem> = updateSystems().iterator()
		profiler.update().use { updateProfiler ->
			while(iter.hasNext()) {
				val s = iter.next()
				use(updateProfiler.start(s)) { s.update() }
			}
		}
	}

	constructor(profiler: SystemsProfiler, systems: List<out ECSSystem>) : this(
		profiler,
		systems.filterIsInstance<InitSystem>(),
		systems.filterIsInstance<UpdateSystem>(),
		systems.filterIsInstance<DestroySystem>()
	)

	override fun init(properties: SceneProperties) {
		val iter: Iterator<InitSystem> = initSystems().iterator()
		profiler.init().use { updateProfiler ->
			while(iter.hasNext()) {
				val s = iter.next()
				use(updateProfiler.start(s)) { s.init(properties) }
			}
		}
	}

	override fun destroy() {
		val iter: Iterator<DestroySystem> = destroySystems().iterator()
		profiler.destroy().use { updateProfiler ->
			while(iter.hasNext()) {
				val s = iter.next()
				use(updateProfiler.start(s)) { s.destroy() }
			}
		}
	}
}