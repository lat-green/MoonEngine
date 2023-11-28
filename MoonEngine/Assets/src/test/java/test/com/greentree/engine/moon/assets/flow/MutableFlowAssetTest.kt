package test.com.greentree.engine.moon.assets.flow

import com.greentree.engine.moon.assets.flow.ConstFlowAsset
import com.greentree.engine.moon.assets.flow.FlattenFlowAsset
import com.greentree.engine.moon.assets.flow.FlowAsset
import com.greentree.engine.moon.assets.flow.MapFlowAsset
import com.greentree.engine.moon.assets.flow.MutableFlowAsset
import com.greentree.engine.moon.assets.flow.merge
import com.greentree.engine.moon.assets.flow.value
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MutableFlowAssetTest {

	@Test
	fun testMerge() = runBlocking {
		val a = MutableFlowAsset("A1")
		val b = MutableFlowAsset("B1")
		val m = merge(a, b)
		val job = launch {
			m.collect {
				println(it)
			}
		}
		delay(10)
		a.setValue("A2")
		delay(10)
		job.cancel()
	}

	@Test
	fun setValue() = runBlocking {
		val a = MutableFlowAsset("A")
		Assertions.assertEquals(a.value(), "A")
		a.value = "B"
		Assertions.assertEquals(a.value(), "B")
	}

	@Test
	fun testMap() = runBlocking {
		val s = MutableFlowAsset("A")
		val m = MapFlowAsset(s) { "($it)" }
		Assertions.assertEquals(m.value(), "(A)")
		s.setValue("B")
		Assertions.assertEquals(m.value(), "(B)")
	}

	@Test
	fun testFlatten() = runBlocking {
		val a = MutableFlowAsset("A1")
		val b = ConstFlowAsset("B1")
		val c: MutableFlowAsset<FlowAsset<String>> = MutableFlowAsset(a)
		val m = FlattenFlowAsset(c)
		Assertions.assertEquals(m.value(), "A1")
		a.setValue("A2")
		Assertions.assertEquals(m.value(), "A2")
		c.setValue(b)
		Assertions.assertEquals(m.value(), "B1")
		a.setValue("A3")
		Assertions.assertEquals(m.value(), "B1")
	}
}

private fun <T> MutableFlowAsset<T>.setValue(value: T) {
	this.value = value
}
