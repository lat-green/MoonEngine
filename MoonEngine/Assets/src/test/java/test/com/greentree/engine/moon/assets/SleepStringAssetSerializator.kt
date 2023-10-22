package test.com.greentree.engine.moon.assets

import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.ConstAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import java.lang.Thread.*

data class SleepStringAssetSerializator(val SLEEP_ON_LOAD: Long) : AssetSerializator<String> {

	override fun load(manager: AssetManager, key: AssetKey): Asset<String>? {
		if(key is StringAssetKey) {
			sleep(SLEEP_ON_LOAD)
			return ConstAsset(key.value)
		}
		return null
	}
}