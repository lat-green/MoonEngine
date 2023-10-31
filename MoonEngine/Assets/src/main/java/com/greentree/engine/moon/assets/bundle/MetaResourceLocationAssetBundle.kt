package com.greentree.engine.moon.assets.bundle

import com.greentree.commons.data.resource.location.ResourceLocation
import com.greentree.commons.data.resource.location.RootFileResourceLocation
import com.greentree.commons.reflection.info.TypeInfo
import java.io.ObjectInputStream

class MetaResourceLocationAssetBundle(private val location: ResourceLocation): AssetBundle {

	override fun <T : Any> load(type: TypeInfo<T>, name: String) = location.getResource(name).open().use {
		ObjectInputStream(it).use {
			it.readObject() as T
		}
	}

	override fun has(name: String) = location.isExist(name)
}
