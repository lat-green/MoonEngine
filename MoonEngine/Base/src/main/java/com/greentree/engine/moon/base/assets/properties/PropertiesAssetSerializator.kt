package com.greentree.engine.moon.base.assets.properties

import com.greentree.commons.data.resource.Resource
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import java.util.*

class PropertiesAssetSerializator : AssetSerializator<Properties> {

	override fun load(context: AssetLoader.Context, ckey: AssetKey): Asset<Properties> {
		val res = context.load<Resource>(ckey)
		return res.map(ResourceToProperties)
	}

	private object ResourceToProperties : Value1Function<Resource, Properties> {

		override fun apply(res: Resource): Properties {
			val prop = Properties()
			res.open().use { prop.load(it) }
			return prop
		}

		override fun toString(): String {
			return "ResourceToProperties"
		}
	}
}