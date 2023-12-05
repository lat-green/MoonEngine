package com.greentree.engine.moon.assets.asset

import com.greentree.engine.moon.assets.SourceNotValid

class CacheAsset<T : Any>(
	val origin: Asset<T>,
) : Asset<T> {

	private var needUpdate: Boolean = false
	private var exception: Exception? = null
	private var cache: T? = null
	private var originLastUpdate = 0L

	init {
		originLastUpdate = origin.lastModified
		if(origin.isValid())
			try {
				cache = origin.value
			} catch(e: Exception) {
				exception = e
			}
		else
			exception = SourceNotValid
	}

	override val value: T
		get() {
			tryUpdate()
			val exception = exception
			if(exception != null && cache == null) {
				if(exception is SourceNotValid) {
					origin.value
					throw RuntimeException("not valid origin: $origin")
				}
				throw RuntimeException("origin:$origin", exception)
			}
			val value = cache
			require(value != null) { "$origin" }
			return value
		}
	override var lastModified: Long = System.currentTimeMillis()
		private set
		get() {
			trySetUpdate()
			return field
		}

	override fun isValid(): Boolean {
		if(cache != null)
			return true
		if(!origin.isValid())
			return false
		tryUpdate()
		return (exception == null)
	}

	override fun isConst() = origin.isConst()

	private fun trySetUpdate() {
		if(!needUpdate) {
			var sourceLastModified = origin.lastModified
			if(originLastUpdate < sourceLastModified) {
				originLastUpdate = sourceLastModified
				lastModified = System.currentTimeMillis()
				needUpdate = true
			}
		}
	}

	private fun tryUpdate() {
		trySetUpdate()
		if(needUpdate) {
			needUpdate = false
			if(origin.isValid())
				try {
					this.cache = origin.value
					exception = null
				} catch(e: Exception) {
					exception = e
					cache = null
				}
			else {
				cache = null
				exception = SourceNotValid
			}
		}
	}
}