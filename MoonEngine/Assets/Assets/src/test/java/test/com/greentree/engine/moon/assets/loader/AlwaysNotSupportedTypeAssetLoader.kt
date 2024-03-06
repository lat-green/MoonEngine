package test.com.greentree.engine.moon.assets.loader

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.exception.NotSupportedType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader

data object AlwaysNotSupportedTypeAssetLoader : AssetLoader {

	override fun <T : Any> load(context: AssetLoader.Context, type: TypeInfo<T>, key: AssetKey): T {
		throw NotSupportedType
	}
}