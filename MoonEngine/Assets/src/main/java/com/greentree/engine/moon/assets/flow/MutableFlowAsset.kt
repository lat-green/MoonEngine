package com.greentree.engine.moon.assets.flow

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.yield

class MutableFlowAsset<T>(var value: T) : FlowAsset<T> {

	override suspend fun collect(collector: FlowCollector<T>) {
		while(true) {
			val v = value
			collector.emit(v)
			while(v == value)
				yield()
		}
	}
}
