package com.greentree.engine.moon.assets.asset

import java.io.ObjectStreamException

class NotValidAsset<T : Any> private constructor() : Asset<T> {

	override val value: T
		get() {
			throw UnsupportedOperationException("NotValidAsset.value")
		}
	override val lastModified
		get() = 0L

	override fun isValid() = false
	override fun isConst() = true
	override fun isCache() = true

	@Throws(ObjectStreamException::class)
	private fun readResolve(): Any {
		return INSTANCE
	}

	companion object {

		private const val serialVersionUID = 1L
		private val INSTANCE: NotValidAsset<*> = NotValidAsset<Any>()
		fun <T : Any> instance(): NotValidAsset<T> {
			return INSTANCE as NotValidAsset<T>
		}
	}
}