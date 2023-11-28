package com.greentree.engine.moon.assets.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.currentCoroutineContext

interface ChannelAsset<out T> {

	suspend fun open(): ReceiveChannel<T>
}

suspend inline fun <R> currentCoroutineScope(crossinline block: suspend CoroutineScope.() -> R) =
	CoroutineScope(currentCoroutineContext()).block()

suspend fun <T> ChannelAsset<T>.value() =
	currentCoroutineScope {
		val channel = open()
		val result = channel.receive()
		channel.cancel()
		result
	}