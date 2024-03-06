package test.com.greentree.engine.moon.assets

import com.greentree.commons.reflection.info.TypeInfoBuilder.*
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.manager.AssetManager
import com.greentree.engine.moon.assets.manager.MutableAssetManager
import com.greentree.engine.moon.assets.manager.SimpleAssetManager
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

private typealias LoadRequest<T> = Pair<Class<T>, AssetKey>

class AssetTest {

	private lateinit var manager: MutableAssetManager

	data object StringToIntAssetSerializator : AssetSerializator<Int> {

		override fun load(manager: AssetLoader.Context, key: AssetKey): Int {
			val str = manager.load<String>(key)
			return str.toInt()
		}
	}

	@MethodSource(value = ["requests"])
	@ParameterizedTest
	fun <T : Any> AssetManager_double_load(pair: Pair<LoadRequest<T>, out T>) {
		val request = pair.first
		val result = pair.second
		val res1 = manager.loadAsset(request)
		val res2 = manager.loadAsset(request)
		assertEquals(res1, res2)
		assertEquals(res1.value, result)
		assertEquals(res2.value, result)
	}

	@MethodSource(value = ["requests"])
	@ParameterizedTest
	fun <T : Any> AssetManager_load(pair: Pair<LoadRequest<T>, out T>) {
		val request = pair.first
		val result = pair.second
		val res = manager.loadAsset(request)
		assertEquals(res.value, result)
	}

	@MethodSource(value = ["map_requests"])
	@ParameterizedTest
	fun <T : Any> AssetManager_map_load(pair: Pair<LoadRequest<T>, out T>) {
		val res = manager.loadAsset(pair.first)
		assertEquals(res.value, pair.second)
	}

	@BeforeEach
	fun setup() {
		manager = SimpleAssetManager()
		manager.addSerializator(StringToIntAssetSerializator)
	}

	companion object {

		@JvmStatic
		fun map_requests(): Stream<Pair<LoadRequest<*>, *>> {
			val res: ArrayList<Pair<LoadRequest<*>, *>> = ArrayList()
			for(n in intArrayOf(56, 89, -4)) {
				val key = ResultAssetKey(n.toString() + "")
				res.add(Pair(LoadRequest(Int::class.java, key), n))
				res.add(Pair(LoadRequest(Int::class.java, ResultAssetKey("" + n)), n))
				res.add(Pair(LoadRequest(Int::class.java, ResultAssetKey(n)), n))
			}
			return res.stream()
		}

		@JvmStatic
		fun requests(): Stream<Pair<LoadRequest<*>, *>> {
			val res: ArrayList<Pair<LoadRequest<*>, *>> = ArrayList()
			for(str in arrayOf<String>("12345", "Hello")) {
				val key = ResultAssetKey(str)
				res.add(
					Pair(
						LoadRequest(
							String::class.java, key
						), str
					)
				)
			}
			return res.stream()
		}
	}
}

fun <T : Any> AssetManager.loadAsset(request: LoadRequest<T>): Asset<T> = loadAsset(getTypeInfo(request.first), request.second)