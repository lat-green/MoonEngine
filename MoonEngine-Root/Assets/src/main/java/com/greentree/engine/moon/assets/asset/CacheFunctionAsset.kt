package com.greentree.engine.moon.assets.asset

class CacheFunctionAsset<T : Any, R : Any> private constructor(
	private val source: Asset<T>,
	private val function: Value1Function<T, R>,
) : Asset<R> {

	private var needUpdate: Boolean = false
	private var exception: Exception? = null
	private var cache: R? = null
	private var sourceLastUpdate = 0L

	init {
		sourceLastUpdate = source.lastModified
		if(source.isValid())
			try {
				cache = function(source.value)
			} catch(e: Exception) {
				exception = e
			}
		else
			exception = SourceNotValid
	}

	override val value: R
		get() {
			tryUpdate()
			val exception = exception
			if(exception != null && cache == null) {
				if(exception is SourceNotValid) {
					source.value
					throw RuntimeException("not valid source: $source")
				}
				throw RuntimeException("source:$source function:$function", exception)
			}
			val value = cache
			require(value != null) { "$function(${source.value})" }
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
		if(!source.isValid())
			return false
		tryUpdate()
		return (exception == null)
	}

	override fun isConst() = source.isConst()

	private fun trySetUpdate() {
		if(!needUpdate) {
			var sourceLastModified = source.lastModified
			if(sourceLastUpdate < sourceLastModified) {
				sourceLastUpdate = sourceLastModified
				lastModified = System.currentTimeMillis()
				needUpdate = true
			}
		}
	}

	private fun tryUpdate() {
		trySetUpdate()
		if(needUpdate) {
			needUpdate = false
			if(source.isValid())
				try {
					val cache = cache
					this.cache =
						if(cache != null)
							function(source.value, cache)
						else
							function(source.value)
					exception = null
				} catch(e: Exception) {
					exception = e
//					cache = null
				}
			else {
//				cache = null
				exception = SourceNotValid
			}
		}
	}

	override fun toString(): String {
		return "Function[${function}]($source)"
	}

	companion object {

		fun <T : Any, R : Any> newAsset(
			asset: Asset<T>,
			function: Value1Function<T, R>,
		) =
			if(asset.isConst())
				ConstAsset(function(asset.value))
			else
				CacheFunctionAsset(asset, function)
	}
}