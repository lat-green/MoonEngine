package com.greentree.engine.moon.base.assets.text

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.util.InputStreamUtil
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator

object ResourceToTextSerializator : AssetSerializator<String> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): String {
		val res = manager.load<Resource>(key)
		res.open().use {
			return InputStreamUtil.readString(it)
		}
	}
}