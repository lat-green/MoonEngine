package test.com.greentree.engine.moon.assets.react

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.reflection.info.TypeInfoBuilder
import com.greentree.commons.reflection.info.TypeUtil
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.NotSupportedType
import com.greentree.engine.moon.assets.asset.CacheAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.react.BaseReactAssetManager
import com.greentree.engine.moon.assets.react.getValue
import com.greentree.engine.moon.assets.react.loader.ReactAssetLoader
import com.greentree.engine.moon.assets.react.loader.load
import com.greentree.engine.moon.assets.react.setValue
import com.greentree.engine.moon.assets.react.useEffect
import com.greentree.engine.moon.assets.react.useState
import com.greentree.engine.moon.assets.serializator.loader.load
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

class BaseReactAssetManagerTest {

	@Test
	fun test1() {
		val manager = BaseReactAssetManager()
		manager.addLoader(object : ReactAssetLoader {
			override suspend fun <T : Any> load(
				context: ReactAssetLoader.Context,
				type: TypeInfo<T>,
				key: AssetKey,
			) = with(context) {
				if(!TypeUtil.isExtends(TypeInfoBuilder.getTypeInfo(String::class.java).boxing, type))
					throw NotSupportedType
				if(key !is ResultAssetKey)
					throw NotSupportedKeyType
				var count by useState(1)
				useEffect {
					launch {
						delay(100)
						count++
					}
				}
				(key.result as String).repeat(count) as T
			}
		})
		manager.addLoader(object : ReactAssetLoader {
			override suspend fun <T : Any> load(
				context: ReactAssetLoader.Context,
				type: TypeInfo<T>,
				key: AssetKey,
			): T {
				if(!TypeUtil.isExtends(TypeInfoBuilder.getTypeInfo(BigInteger::class.java).boxing, type))
					throw NotSupportedType
				return context.load<String>(key).toBigInteger() as T
			}
		})
		val a = CacheAsset(manager.load<BigInteger>(ResultAssetKey("1")))
		assertEquals(a.value, 11.toBigInteger())
	}
}

