package com.greentree.engine.moon.assets.flow

import kotlinx.coroutines.flow.FlowCollector

class MapFlowAsset<T, R>(
	val source: FlowAsset<T>,
	val function: suspend (T) -> R,
) : FlowAsset<R> {

	override suspend fun collect(collector: FlowCollector<R>) = source.collect {
		collector.emit(function(it))
	}
}

fun <T, R> FlowAsset<T>.map(block: (T) -> R): FlowAsset<R> = MapFlowAsset(this, block)