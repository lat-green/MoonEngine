package com.greentree.engine.moon.ecs.system.debug

import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.system.DestroySystem
import com.greentree.engine.moon.ecs.system.InitSystem
import com.greentree.engine.moon.ecs.system.Systems
import com.greentree.engine.moon.ecs.system.UpdateSystem

class DebugSystems(
	private val profiler: SystemsProfiler, initSystems: List<InitSystem>?,
	updateSystems: List<UpdateSystem>?, destroySystems: List<DestroySystem>?
) : Systems(initSystems, updateSystems, destroySystems) {

	override fun update() {
		val iter: Iterator<UpdateSystem> = updateSystems().iterator()
		profiler.update().use { updateFrofiler ->
			while (iter.hasNext()) {
				val s = iter.next()
				updateFrofiler.start(s).use { start -> s.update() }
			}
		}
	}

	override fun init(world: World?) {
		val iter: Iterator<InitSystem> = initSystems().iterator()
		profiler.init().use { updateFrofiler ->
			while (iter.hasNext()) {
				val s = iter.next()
				updateFrofiler.start(s).use { start -> s.init(world) }
			}
		}
	}

	override fun destroy() {
		val iter: Iterator<DestroySystem> = destroySystems().iterator()
		profiler.destroy().use { updateFrofiler ->
			while (iter.hasNext()) {
				val s = iter.next()
				updateFrofiler.start(s).use { start -> s.destroy() }
			}
		}
	}
}