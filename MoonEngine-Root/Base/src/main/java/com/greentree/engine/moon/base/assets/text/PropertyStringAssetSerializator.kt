package com.greentree.engine.moon.base.assets.text

import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value2Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import java.util.*

class PropertyStringAssetSerializator : AssetSerializator<String> {

	override fun load(manager: AssetManager, key: AssetKey): Asset<String>? {
		if(key is PropertyAssetKey) {
			val prop = manager.load<Properties>(key.properties)
			val name = manager.load<String>(key.name)
			return map(prop, name, GetPropertyFunction)
		}
		return null
	}

	private object GetPropertyFunction : Value2Function<Properties, String, String> {

		override fun apply(properties: Properties, name: String): String {
			if(properties.containsKey(name))
				return properties.getProperty(name)
			throw NullPointerException("properties not have property properties: $properties, name: $name")
		}

		override fun toString(): String {
			return "GetPropertyFunction"
		}
	}
}