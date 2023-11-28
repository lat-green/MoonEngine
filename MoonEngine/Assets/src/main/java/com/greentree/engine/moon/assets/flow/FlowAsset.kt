package com.greentree.engine.moon.assets.flow

import com.greentree.engine.moon.assets.channels.currentCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

interface FlowAsset<out T> {

	suspend fun collect(collector: FlowCollector<T>)
}

fun <T> CoroutineScope.value(asset: FlowAsset<T>, collector: FlowCollector<T>) = launch {
	asset.collect(collector)
}

suspend fun <T> FlowAsset<T>.value() = currentCoroutineScope {
	val resultChannel = Channel<T>()
	val job = launch {
		collect {
			resultChannel.send(it)
		}
	}
	val result = resultChannel.receive()
	job.cancel()
	result
}