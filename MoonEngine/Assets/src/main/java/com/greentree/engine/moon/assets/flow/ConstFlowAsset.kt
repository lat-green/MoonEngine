package com.greentree.engine.moon.assets.flow

import kotlinx.coroutines.flow.FlowCollector

data class ConstFlowAsset<T>(val value: T) : FlowAsset<T> {

	override suspend fun collect(collector: FlowCollector<T>) {
		collector.emit(value)
	}
}