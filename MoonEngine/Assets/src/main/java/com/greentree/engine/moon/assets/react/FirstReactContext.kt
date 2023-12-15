package com.greentree.engine.moon.assets.react

data class FirstReactContext(
	val refs: MutableList<Pair<Ref<*>, () -> Unit>> = mutableListOf(),
	val onRefresh: () -> Unit,
) : ReactContext {

	private data object Null

	override fun <T> useRefWithDestroy(initialValue: () -> T, onDestroy: () -> Unit) =
		Ref(initialValue()).also { refs.add(it to onDestroy) }

	override fun <T> useRefWithDestroy(onDestroy: () -> Unit) = Ref(Null).also { refs.add(it to onDestroy) } as Ref<T>

	override fun useRefresh() {
		onRefresh()
	}
}