package com.greentree.engine.moon.assets.react

import kotlinx.coroutines.CoroutineScope

suspend fun <R : Any> currentCoroutineContext(block: CoroutineScope.() -> R): R {
	return CoroutineScope(kotlinx.coroutines.currentCoroutineContext()).block()
}
