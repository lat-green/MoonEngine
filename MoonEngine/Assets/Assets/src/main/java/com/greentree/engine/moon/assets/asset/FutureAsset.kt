package com.greentree.engine.moon.assets.asset

import java.util.concurrent.ExecutionException
import java.util.concurrent.Future

data class FutureAsset<T : Any>(
	private val result: Future<T>,
) : Asset<T> {

	private val valueResult: Result<T> by lazy {
		kotlin.runCatching {
			try {
				result.get()
			} catch(e: ExecutionException) {
				throw e.cause!!
			}
		}
	}
	override val value = valueResult.getOrThrow()
}
