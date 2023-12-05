package com.greentree.engine.moon.assets.react

import kotlin.reflect.KProperty

interface ReactContext {

	fun useRefresh()
	fun <T> useContextRef(close: () -> Unit): Ref<T> = useContextRef(NullDependency, close) as Ref<T>
	fun <T> useContextRef(initialValue: T, close: () -> Unit): Ref<T>
}

data class SmartReactContext(
	val refs: MutableList<Pair<Ref<*>, () -> Unit>> = mutableListOf(),
	val onRefresh: () -> Unit,
) : ReactContext {

	private var refCount = 0
	override fun useRefresh() = onRefresh()

	override fun <T> useContextRef(initialValue: T, close: () -> Unit): Ref<T> {
		if(refCount < refs.size)
			return refs[refCount++].first as Ref<T>
		refCount++
		return Ref(initialValue).also { refs.add(it to close) }
	}
}

data class HeadReactContext(
	val refs: MutableList<Pair<Ref<*>, () -> Unit>> = mutableListOf(),
	val onRefresh: () -> Unit,
) : ReactContext {

	override fun useRefresh() = onRefresh()

	override fun <T> useContextRef(initialValue: T, close: () -> Unit) =
		Ref(initialValue).also { refs.add(it to close) }
}

data class TailReactContext(
	val refs: List<Pair<Ref<*>, () -> Unit>>,
	val onRefresh: () -> Unit,
) : ReactContext {

	private var refCount = 0

	override fun useRefresh() = onRefresh()

	override fun <T> useContextRef(initialValue: T, close: () -> Unit) = refs[refCount++].first as Ref<T>
}

fun <T> ReactContext.useRef(): Ref<T> = useContextRef {}
fun <T> ReactContext.useRef(initialValue: T): Ref<T> = useContextRef(initialValue) {}

data class Ref<T>(var value: T)

operator fun <T> Ref<T>.getValue(nothing: Nothing?, property: KProperty<*>) = value
operator fun <T> Ref<T>.setValue(nothing: Nothing?, property: KProperty<*>, value: T) {
	this.value = value
}

interface State<T> {

	var value: T
}

operator fun <T> State<T>.getValue(nothing: Nothing?, property: KProperty<*>) = value
operator fun <T> State<T>.setValue(nothing: Nothing?, property: KProperty<*>, value: T) {
	this.value = value
}

fun <T> ReactContext.useState(initialValue: T): State<T> {
	var ref by useRef(initialValue)
	return object : State<T> {
		override var value: T
			get() = ref
			set(value) {
				ref = value
				useRefresh()
			}
	}
}

object NullDependency

inline fun ReactContext.useEffect(dependency: Any = Unit, block: () -> Unit) {
	var dependencyRef: Any by useRef(NullDependency)
	if(dependencyRef != dependency) {
		dependencyRef = dependency
		block()
	}
}

inline fun <R : Any> ReactContext.useMemo(dependency: Any, block: () -> R): R {
	var dependencyRef: Any by useRef()
	var memoRef by useRef<R>()
	if(dependencyRef != dependency) {
		dependencyRef = dependency
		memoRef = block()
	}
	return memoRef
}
