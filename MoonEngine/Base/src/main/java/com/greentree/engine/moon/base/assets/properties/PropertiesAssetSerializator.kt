package com.greentree.engine.moon.base.assets.properties

import com.greentree.commons.data.resource.Resource
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import java.util.*

object PropertiesAssetSerializator : AssetSerializator<Properties> {

	override fun load(context: AssetLoader.Context, key: AssetKey): Properties {
		val res = context.load<Resource>(key)
		val properties = Properties()
		res.open().use {
			properties.load(it)
		}
		return properties
	}
}