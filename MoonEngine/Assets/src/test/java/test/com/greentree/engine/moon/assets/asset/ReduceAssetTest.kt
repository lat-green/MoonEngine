package test.com.greentree.engine.moon.assets.asset

import com.greentree.engine.moon.assets.asset.MutableAsset
import com.greentree.engine.moon.assets.asset.ReduceAsset
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import java.lang.Thread.*

class ReduceAssetTest {

	@RepeatedTest(20)
	fun setValueTest() {
		val asset1 = MutableAsset("1")
		sleep(SLEEP)
		val asset2 = MutableAsset("2")
		sleep(SLEEP)
		val reduce = ReduceAsset(asset1)
		assertEquals(reduce.value, asset1.value)
		assertTrue(reduce.lastModified - asset1.lastModified < 100)
		assertTrue(reduce.lastModified - asset1.lastModified >= 0)
		sleep(SLEEP)
		val lastModified = reduce.lastModified
		reduce.asset = asset2
		assertNotEquals(reduce.lastModified, lastModified)
		assertTrue(reduce.lastModified - asset1.lastModified < 100)
		assertTrue(reduce.lastModified - asset1.lastModified >= 0)
		assertEquals(reduce.value, asset2.value)
	}

	companion object {

		private const val SLEEP = 2L
	}
}