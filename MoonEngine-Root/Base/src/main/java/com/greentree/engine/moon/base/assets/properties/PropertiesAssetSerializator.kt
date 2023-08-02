package com.greentree.engine.moon.base.assets.properties

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.util.exception.WrappedException
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import java.io.IOException
import java.util.*

class PropertiesAssetSerializator : AssetSerializator<Properties> {

	override fun canLoad(manager: AssetManager, key: AssetKey): Boolean {
		return manager.canLoad(Resource::class.java, key)
	}

	override fun load(context: AssetManager, ckey: AssetKey): Asset<Properties>? {
		run {
			val res = context.load(
				Resource::class.java, ckey
			)
			if(res != null) return res.map(ResourceToProperties)
		}
		return null
	}

	private object ResourceToProperties : Value1Function<Resource, Properties> {

		override fun apply(res: Resource): Properties {
			val prop = Properties()
			try {
				res.open().use { `in` -> prop.load(`in`) }
			} catch(e: IOException) {
				throw WrappedException(e)
			}
			return prop
		}

		override fun toString(): String {
			return "ResourceToProperties"
		}
	}
}