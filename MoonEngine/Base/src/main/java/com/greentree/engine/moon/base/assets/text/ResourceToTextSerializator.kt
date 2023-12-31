package com.greentree.engine.moon.base.assets.text

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.util.InputStreamUtil
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

object ResourceToTextSerializator : AssetSerializator<String> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): AssetProvider<String> {
		val res = manager.load<Resource>(key)
		return res.map(ResourceToText)
	}

	object ResourceToText : Value1Function<Resource, String> {

		private fun readResolve(): Any = ResourceToText

		override fun apply(res: Resource): String {
			return res.open().use { InputStreamUtil.readString(it) }
		}

		override fun toString(): String {
			return "ResourceToText"
		}
	}
}