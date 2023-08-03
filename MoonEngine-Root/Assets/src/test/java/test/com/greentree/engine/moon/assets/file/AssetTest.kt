package test.com.greentree.engine.moon.assets.file

import com.greentree.commons.util.cortege.Pair
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ConstAsset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.asset.isValid
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.BaseAssetManager
import com.greentree.engine.moon.assets.serializator.manager.MutableAssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import com.greentree.engine.moon.assets.serializator.marker.CantLoadType
import com.greentree.engine.moon.assets.serializator.marker.NotMyKeyType
import com.greentree.engine.moon.assets.serializator.request.KeyLoadRequest
import com.greentree.engine.moon.assets.serializator.request.KeyLoadRequestImpl
import com.greentree.engine.moon.assets.serializator.request.load
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Timeout
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.concurrent.TimeUnit
import java.util.stream.Stream

class AssetTest {

	private lateinit var manager: MutableAssetManager

	class StringAssetSerializator : AssetSerializator<String> {

		override fun load(context: AssetManager, key: AssetKey): Asset<String> {
			if(key is StringAssetKey) {
				try {
					Thread.sleep(SLEEP_ON_LOAD)
				} catch(e: InterruptedException) {
					e.printStackTrace()
				}
				return ConstAsset(key.value)
			}
			throw NotMyKeyType
		}
	}

	class StringToIntAssetSerializator : AssetSerializator<Int> {

		override fun load(manager: AssetManager, key: AssetKey): Asset<Int> {
			val str = manager.load<String>(key)
			if(str.isValid)
				return str.map(StringToInt())
			throw CantLoadType.get<String>()
		}

		private class StringToInt : Value1Function<String, Int> {

			override fun apply(value: String): Int {
				return value.toInt()
			}
		}
	}

	data class StringAssetKey(val value: String) : AssetKey

	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = ["requests"])
	@ParameterizedTest
	fun <T> AssetManager_double_load(pair: Pair<KeyLoadRequestImpl<out T>, out T>) {
		val request: KeyLoadRequestImpl<out T> = pair.first
		val result = pair.seconde
		manager.addSerializator<String>(StringAssetSerializator())
		val res1 = manager.load(request)
		val res2 = manager.load(request)
		assertEquals(res1, res2)
		assertEquals(res1.value, result)
		assertEquals(res2.value, result)
	}

	@MethodSource(value = ["requests"])
	@ParameterizedTest
	fun <T> AssetManager_load(pair: Pair<KeyLoadRequestImpl<out T>, out T>) {
		val request: KeyLoadRequestImpl<out T> = pair.first
		val result = pair.seconde
		manager.addSerializator<String>(StringAssetSerializator())
		val res = manager.load(request)
		assertEquals(res.value, result)
	}

	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = ["requests"])
	@ParameterizedTest
	fun <T> AssetManager_load_with_Default(
		pair: Pair<KeyLoadRequestImpl<out T>, out T>,
	) {
		val request: KeyLoadRequestImpl<out T> = pair.first
		val result = pair.seconde
		val DEF_STR = "DEF_STR"
		manager.addSerializator(StringAssetSerializator())
		val res = manager.load(request)
		assertEquals(res.value, result)
	}

	@Timeout(value = TIMEOUT, unit = TimeUnit.MILLISECONDS)
	@MethodSource(value = ["map_requests"])
	@ParameterizedTest
	fun <T : Any> AssetManager_map_load(pair: Pair<KeyLoadRequest<out T>, out T>) {
		manager.addSerializator(StringAssetSerializator())
		manager.addSerializator(StringToIntAssetSerializator())
		val res = manager.load(pair.first)
		assertEquals(res.value, pair.seconde)
	}

	@BeforeEach
	fun setup() {
		manager = BaseAssetManager()
	}

	companion object {

		private const val SLEEP_ON_LOAD: Long = 40
		private const val TIMEOUT = 2 * SLEEP_ON_LOAD - 1

		@JvmStatic
		fun map_requests(): Stream<Pair<KeyLoadRequestImpl<*>, *>> {
			val res: ArrayList<Pair<KeyLoadRequestImpl<*>, *>> = ArrayList<Pair<KeyLoadRequestImpl<*>, *>>()
			for(n in intArrayOf(56, 89, -4)) {
				val key = StringAssetKey(n.toString() + "")
				res.add(Pair(KeyLoadRequestImpl(Int::class.java, key), n))
				res.add(Pair(KeyLoadRequestImpl(Int::class.java, ResultAssetKey("" + n)), n))
				res.add(Pair(KeyLoadRequestImpl(Int::class.java, ResultAssetKey(n)), n))
			}
			return res.stream()
		}

		@JvmStatic
		fun requests(): Stream<Pair<KeyLoadRequestImpl<*>, *>> {
			val res: ArrayList<Pair<KeyLoadRequestImpl<*>, *>> = ArrayList<Pair<KeyLoadRequestImpl<*>, *>>()
			for(str in arrayOf<String>("12345", "Hello")) {
				val key = StringAssetKey(str)
				res.add(
					Pair<KeyLoadRequestImpl<*>, String>(
						KeyLoadRequestImpl<String>(
							String::class.java, key
						), str
					)
				)
			}
			return res.stream()
		}
	}
}