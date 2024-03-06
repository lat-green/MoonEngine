package com.greentree.engine.moon.script.assets

import com.greentree.commons.data.resource.Resource
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.script.javascript.JavaScriptScript
import org.mozilla.javascript.WrappedException
import java.io.IOException

data object ScriptAssetSerializator : AssetSerializator<JavaScriptScript> {

	override fun load(context: AssetLoader.Context, key: AssetKey): JavaScriptScript {
		val resource = context.load<Resource>(key)
		val script: String
		try {
			resource.open().use { `in` ->
				script = String(`in`.readAllBytes())
			}
		} catch(e: IOException) {
			throw WrappedException(e)
		}
		return JavaScriptScript(resource.name, script)
	}
}
