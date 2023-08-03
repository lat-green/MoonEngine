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
		assertEquals(reduce.lastModified, asset1.lastModified)
		sleep(SLEEP)
		reduce.asset = asset2
		assertEquals(reduce.lastModified, System.currentTimeMillis())
		assertNotEquals(reduce.lastModified, asset2.lastModified)
		assertEquals(reduce.value, asset2.value)
	}

	companion object {

		private const val SLEEP = 1L
	}
}