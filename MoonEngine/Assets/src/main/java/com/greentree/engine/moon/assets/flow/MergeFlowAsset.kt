package com.greentree.engine.moon.assets.flow

import com.greentree.engine.moon.assets.asset.Group2
import com.greentree.engine.moon.assets.asset.group
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

//fun <T1, T2> merge(
//	source1: FlowAsset<T1>,
//	source2: FlowAsset<T2>,
//) = MergeFlowAsset(source1, source2)
fun <T1, T2> merge(
	source1: FlowAsset<T1>,
	source2: FlowAsset<T2>,
) = source1.map { t1 ->
	println(t1)
	source2.map { t2 ->
		group(t1, t2)
	}
}.flatten()

data class MergeFlowAsset<T1, T2>(
	val source1: FlowAsset<T1>,
	val source2: FlowAsset<T2>,
) : FlowAsset<Group2<T1, T2>> {

	override suspend fun collect(collector: FlowCollector<Group2<T1, T2>>) {
		source1.collect(MergeCollector(source2, collector))
	}

	class MergeCollector<T1, T2>(
		private val source2: FlowAsset<T2>,
		private val collector: FlowCollector<Group2<T1, T2>>,
	) :
		FlowCollector<T1> {

		private lateinit var job: Job

		override suspend fun emit(t1: T1) {
			println(t1)
			if(::job.isInitialized)
				job.cancel()
			coroutineScope {
				job = launch {
					source2.collect { t2 ->
						collector.emit(group(t1, t2))
					}
				}
			}
		}
	}
}