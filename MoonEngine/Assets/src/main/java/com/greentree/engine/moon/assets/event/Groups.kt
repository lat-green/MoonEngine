package com.greentree.engine.moon.assets.event

import com.greentree.commons.action.ListenerCloser
import com.greentree.commons.action.observable.RunObservable
import com.greentree.commons.action.observer.run.RunAction
import com.greentree.engine.moon.assets.Group2
import com.greentree.engine.moon.assets.Group3
import com.greentree.engine.moon.assets.group
import com.greentree.engine.moon.assets.provider.request.AssetRequest

fun <T1, T2> merge(
	source1: EventAsset<T1>,
	source2: EventAsset<T2>,
) = Merge2EventAsset(source1, source2)

fun <T1, T2, T3> merge(
	source1: EventAsset<T1>,
	source2: EventAsset<T2>,
	source3: EventAsset<T3>,
) = Merge3EventAsset(source1, source2, source3)

class Merge2EventAsset<T1, T2>(
	private val source1: EventAsset<T1>,
	private val source2: EventAsset<T2>,
) : EventAsset<Group2<T1, T2>> {

	private val lc: ListenerCloser
	private val action = RunAction()

	init {
		lc = ListenerCloser.merge(
			source1.onChange.addListener {
				action.event()
			},
			source2.onChange.addListener {
				action.event()
			}
		)
	}

	override val onChange: RunObservable
		get() = action

	override fun value(request: AssetRequest) = source1.value(request).flatMapCatching { t1 ->
		source2.value(request).mapCatching { t2 ->
			group(t1, t2)
		}
	}

	override fun close() {
		lc.close()
	}
}

class Merge3EventAsset<T1, T2, T3>(
	private val source1: EventAsset<T1>,
	private val source2: EventAsset<T2>,
	private val source3: EventAsset<T3>,
) : EventAsset<Group3<T1, T2, T3>> {

	private val lc: ListenerCloser
	private val action = RunAction()

	init {
		lc = ListenerCloser.merge(
			source1.onChange.addListener {
				action.event()
			},
			source2.onChange.addListener {
				action.event()
			},
			source3.onChange.addListener {
				action.event()
			}
		)
	}

	override val onChange: RunObservable
		get() = action

	override fun value(request: AssetRequest) = source1.value(request).flatMapCatching { t1 ->
		source2.value(request).flatMapCatching { t2 ->
			source3.value(request).mapCatching { t3 ->
				group(t1, t2, t3)
			}
		}
	}

	override fun close() {
		lc.close()
	}
}

inline fun <R, T> Result<T>.flatMapCatching(transform: (T) -> Result<R>) = mapCatching {
	transform(it)
}.flatten()

fun <T> Result<Result<T>>.flatten(): Result<T> {
	return getOrElse {
		Result.failure(it)
	}
}
