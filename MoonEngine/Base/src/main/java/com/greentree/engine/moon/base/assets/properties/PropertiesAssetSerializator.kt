package com.greentree.engine.moon.base.assets.properties

import com.greentree.commons.data.resource.Resource
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import java.util.*

object PropertiesAssetSerializator : AssetSerializator<Properties> {

	override fun load(context: AssetLoader.Context, key: AssetKey): AssetProvider<Properties> {
		val res = context.load<Resource>(key)
		return res.map(ResourceToProperties)
	}

	private object ResourceToProperties : Value1Function<Resource, Properties> {

		override fun applyWithDest(value: Resource, dest: Properties): Properties {
			value.open().use { dest.load(it) }
			return dest
		}

		override fun apply(res: Resource): Properties {
			val prop = Properties()
			return applyWithDest(res, prop)
		}

		override fun toString(): String {
			return "ResourceToProperties"
		}
	}
}