package test.com.greentree.engine.moon.assets.react

import com.greentree.engine.moon.assets.react.HeadReactContext
import com.greentree.engine.moon.assets.react.ReactContext
import com.greentree.engine.moon.assets.react.Ref
import com.greentree.engine.moon.assets.react.SmartReactContext
import com.greentree.engine.moon.assets.react.TailReactContext
import com.greentree.engine.moon.assets.react.useMemo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ReactContextTest {

	private fun ReactContext.componentTest1(a: String) = useMemo(a) {
		"($a)"
	}

	@Test
	fun useMemo_FirstOther() {
		val failRefresh: () -> Unit = { fail() }
		val refs = mutableListOf<Pair<Ref<*>, () -> Unit>>()
		assertEquals(HeadReactContext(refs, failRefresh).componentTest1("A"), "(A)")
		assertEquals(TailReactContext(refs, failRefresh).componentTest1("A"), "(A)")
		assertEquals(TailReactContext(refs, failRefresh).componentTest1("A"), "(A)")
	}

	@Test
	fun useMemo_Smart() {
		val failRefresh: () -> Unit = { fail() }
		val refs = mutableListOf<Pair<Ref<*>, () -> Unit>>()
		assertEquals(SmartReactContext(refs, failRefresh).componentTest1("A"), "(A)")
		assertEquals(SmartReactContext(refs, failRefresh).componentTest1("A"), "(A)")
		assertEquals(SmartReactContext(refs, failRefresh).componentTest1("A"), "(A)")
	}
}