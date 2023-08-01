package com.greentree.engine.moon.base.assets.text

import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value2Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import java.util.*

class PropertyStringAssetSerializator : AssetSerializator<String> {

	override fun canLoad(manager: AssetManager, key: AssetKey): Boolean {
		if(key is PropertyAssetKey) {
			return (manager.canLoad(Properties::class.java, key.properties)
					&& manager.canLoad(String::class.java, key.name))
					&& manager.load(
				Properties::class.java,
				key.properties
			).value.containsKey(manager.load(String::class.java, key.name).value)
		}
		return false
	}

	override fun load(manager: AssetManager, key: AssetKey): Asset<String> {
		if(key is PropertyAssetKey) {
			val prop = manager.load(Properties::class.java, key.properties)
			val name = manager.load(String::class.java, key.name)
			return map(prop, name, PropertyAssetFunction)
		}
		throw IllegalArgumentException()
	}

	private object PropertyAssetFunction : Value2Function<Properties, String, String> {

		override fun apply(prop: Properties, name: String): String {
			return prop.getProperty(name)
		}
	}
}