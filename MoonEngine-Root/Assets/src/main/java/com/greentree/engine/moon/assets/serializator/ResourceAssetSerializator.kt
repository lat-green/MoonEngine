package com.greentree.engine.moon.assets.serializator

import com.greentree.commons.data.resource.Resource
import com.greentree.commons.data.resource.location.ResourceLocation
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.newResourceAsset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load

class ResourceAssetSerializator(private val resources: ResourceLocation) : AssetSerializator<Resource> {

	override fun load(manager: AssetManager, ckey: AssetKey): Asset<Resource>? {
		if(ckey is ResourceAssetKey) {
			val name = manager.load<String>(ckey.resourceName)
			return newResourceAsset(resources, name)
		}
		return null
	}
}