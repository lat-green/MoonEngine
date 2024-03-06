package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.location.ResourceLocation
import com.greentree.engine.moon.assets.exception.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load

class ResourceAssetSerializator(private val resources: ResourceLocation) : AssetSerializator<Resource> {

	override fun load(manager: AssetLoader.Context, ckey: AssetKey): Resource {
		if(ckey is ResourceAssetKey) {
			val resourceName = manager.load<String>(ckey.resourceName)
			return resources.getResource(resourceName)
		}
		throw NotSupportedKeyType
	}
}
