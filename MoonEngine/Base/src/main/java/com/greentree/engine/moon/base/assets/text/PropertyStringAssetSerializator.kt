package com.greentree.engine.moon.base.assets.text

import com.greentree.engine.moon.assets.asset.getValue
import com.greentree.engine.moon.assets.exception.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.loadAsset
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import java.util.*

object PropertyStringAssetSerializator : AssetSerializator<String> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): String {
		if(key is PropertyAssetKey) {
			val properties by manager.loadAsset<Properties>(key.properties)
			val name by manager.loadAsset<String>(key.name)
			if(properties.containsKey(name))
				return properties.getProperty(name)
			throw NullPointerException("properties not have property properties: $properties, name: $name")
		}
		throw NotSupportedKeyType
	}
}