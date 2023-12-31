package test.com.greentree.engine.moon.assets

import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.ConstAssetProvider
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import java.lang.Thread.*

data class SleepStringAssetSerializator(val SLEEP_ON_LOAD: Long) : AssetSerializator<String> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): AssetProvider<String> {
		if(key is StringAssetKey) {
			sleep(SLEEP_ON_LOAD)
			return ConstAssetProvider(key.value)
		}
		throw NotSupportedKeyType
	}
}