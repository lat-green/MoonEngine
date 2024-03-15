package com.greentree.engine.moon.assets.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.key.AssetKey
import org.apache.logging.log4j.LogManager

class LogTotalTimeDebugCallback : DebugAssetManager.DebugCallback {

	companion object {

		private val LOG = LogManager.getLogger(
			LogTotalTimeDebugCallback::class.java
		)

		private fun time() = System.nanoTime() / 1_000_000_000.0
	}

	private val types = mutableMapOf<TypeInfo<*>, Double>()

	override fun loadAsset(type: TypeInfo<*>, key: AssetKey): () -> Unit {
		val startTime = time()
		return {
			val deltaTime = time() - startTime
			val t = types.getOrDefault(type, 0.0) + deltaTime
			types[type] = t
			LOG.info("load $type $key current:%.3f total(for type):%.3f".format(deltaTime, t))
		}
	}
}