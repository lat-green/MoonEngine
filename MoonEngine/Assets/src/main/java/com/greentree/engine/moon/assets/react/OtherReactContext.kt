package com.greentree.engine.moon.assets.react

data class OtherReactContext(
	val refs: List<Pair<Ref<*>, () -> Unit>>,
	val onRefresh: () -> Unit,
) : ReactContext {

	private var count = 0

	override fun <T> useRefWithDestroy(initialValue: () -> T, onDestroy: () -> Unit) =
		refs[count++] as Ref<T>

	override fun <T> useRefWithDestroy(onDestroy: () -> Unit) = refs[count++] as Ref<T>

	override fun useRefresh() {
		onRefresh()
	}
}