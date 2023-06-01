package com.greentree.engine.moon.ecs.system.debug

import java.io.Serializable

interface SystemsProfiler : Serializable {

	fun init(): MethodSystemsProfiler
	fun destroy(): MethodSystemsProfiler
	fun update(): MethodSystemsProfiler
}