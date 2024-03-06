package test.com.greentree.engine.moon.assets.loader

import com.greentree.commons.data.resource.Resource
import com.greentree.engine.moon.assets.serializator.SimpleAssetSerializator

data object ResourceToString : SimpleAssetSerializator<Resource, String> {

	override fun invoke(resource: Resource) = resource.open().use {
		String(it.readAllBytes())
	}
}