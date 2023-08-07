package com.greentree.engine.moon.base.assets.text

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.util.InputStreamUtil
import com.greentree.commons.util.exception.WrappedException
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.canLoad
import com.greentree.engine.moon.assets.serializator.manager.load
import java.io.IOException

class StringAssetSerializator : AssetSerializator<String> {

	override fun load(manager: AssetManager, key: AssetKey): Asset<String>? {
		if(manager.canLoad<Resource>(key)) {
			val res = manager.load<Resource>(key)
			return res.map(ResourceToText)
		}
		return null
	}

	private object ResourceToText : Value1Function<Resource, String> {

		override fun apply(res: Resource): String {
			try {
				res.open().use { `in` -> return InputStreamUtil.readString(`in`) }
			} catch(e: IOException) {
				throw WrappedException(e)
			}
		}

		override fun toString(): String {
			return "ResourceToText"
		}
	}
}