package com.greentree.engine.moon.ecs.system.debug

import com.greentree.engine.moon.ecs.system.ECSSystem

interface MethodSystemsProfiler : AutoCloseable {

	override fun close()
	fun start(s: ECSSystem): StartSystemsProfiler
}