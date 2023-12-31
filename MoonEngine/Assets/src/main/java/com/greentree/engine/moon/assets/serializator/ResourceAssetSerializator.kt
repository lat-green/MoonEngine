package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.location.ResourceLocation
import com.greentree.engine.moon.assets.NotSupportedKeyType
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.newResourceAsset
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

class ResourceAssetSerializator(private val resources: ResourceLocation) : AssetSerializator<Resource> {

	override fun load(manager: AssetLoader.Context, ckey: AssetKey): AssetProvider<Resource> {
		if(ckey is ResourceAssetKey) {
			val name = manager.load<String>(ckey.resourceName)
			return newResourceAsset(resources, name)
		}
		throw NotSupportedKeyType
	}
}
