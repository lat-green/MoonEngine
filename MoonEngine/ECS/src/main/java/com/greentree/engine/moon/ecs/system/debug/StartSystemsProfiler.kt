package com.greentree.engine.moon.ecs.system.debug

interface StartSystemsProfiler : AutoCloseable {

	override fun close()
}