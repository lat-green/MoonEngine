package com.greentree.engine.moon.assets.react

interface ReactContext {

	fun <T> useRefWithDestroy(initialValue: () -> T, onDestroy: () -> Unit): Ref<T>
	fun <T> useRefWithDestroy(initialValue: T, onDestroy: () -> Unit): Ref<T> =
		useRefWithDestroy({ initialValue }, onDestroy)

	fun <T> useRefWithDestroy(onDestroy: () -> Unit): Ref<T>

	fun useRefresh()
}

fun ReactContext.useEffect(dependency: Any = Unit, block: () -> Unit) {
	var dependencyRef by useRef<Any>()
	if(dependency != dependencyRef) {
		dependencyRef = dependency
		block()
	}
}

fun ReactContext.useEffectWithClose(dependency: Any = Unit, block: () -> () -> Unit) {
	var closeRef by useRef(block)
	var dependencyRef by useRefWithDestroy(dependency) { closeRef() }
	if(dependency != dependencyRef) {
		dependencyRef = dependency
		closeRef()
		closeRef = block()
	}
}

fun ReactContext.useEffectWithAutoCloseable(dependency: Any = Unit, block: () -> AutoCloseable) =
	useEffectWithClose(dependency) {
		val close = block()
		return@useEffectWithClose { close.close() }
	}

fun <T> ReactContext.useState(initialValue: () -> T): State<T> {
	var ref by useRef<T>(initialValue)
	return object : State<T> {
		override var value: T
			get() = ref
			set(value) {
				ref = value
				useRefresh()
			}
	}
}

fun <T> ReactContext.useState(initialValue: T): State<T> {
	var ref by useRef<T>(initialValue)
	return object : State<T> {
		override var value: T
			get() = ref
			set(value) {
				ref = value
				useRefresh()
			}
	}
}

fun <T> ReactContext.useState(): State<T> {
	var ref by useRef<T>()
	return object : State<T> {
		override var value: T
			get() = ref
			set(value) {
				ref = value
				useRefresh()
			}
	}
}

fun <T> ReactContext.useRef(initialValue: () -> T) = useRefWithDestroy(initialValue) { }
fun <T> ReactContext.useRef(initialValue: T) = useRefWithDestroy(initialValue) { }
fun <T> ReactContext.useRef() = useRefWithDestroy<T> { }
