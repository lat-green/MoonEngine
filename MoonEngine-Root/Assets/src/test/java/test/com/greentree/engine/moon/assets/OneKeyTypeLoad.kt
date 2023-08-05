package test.com.greentree.engine.moon.assets

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.marker.NotMyKeyType

class OneKeyTypeLoad<T : Any>(override val type: TypeInfo<T>) : AssetSerializator<T> {

	private val types = HashSet<Class<out AssetKey>>()

	override fun load(context: AssetManager, key: AssetKey): Asset<T> {
		val type = key::class.java
		if(type in types)
			throw Error("type ${type.name} repeated")
		types.add(type)
		throw NotMyKeyType
	}
}
