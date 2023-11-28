package com.greentree.engine.moon.assets.flow

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

data class FlattenFlowAsset<T>(
	val source: FlowAsset<FlowAsset<T>>,
) : FlowAsset<T> {

	override suspend fun collect(collector: FlowCollector<T>) = source.collect(FlattenCollector(collector))

	class FlattenCollector<T>(private val collector: FlowCollector<T>) : FlowCollector<FlowAsset<T>> {

		private lateinit var job: Job

		override suspend fun emit(value: FlowAsset<T>) {
			if(::job.isInitialized)
				job.cancel()
			coroutineScope {
				job = launch {
					value.collect(collector)
				}
			}
		}
	}
}

fun <T> FlowAsset<FlowAsset<T>>.flatten(): FlowAsset<T> = FlattenFlowAsset(this)