package com.greentree.engine.moon.collision2d.assets

import com.greentree.commons.geometry.geom2d.IMovableShape2D
import com.greentree.commons.geometry.geom2d.shape.Circle2D
import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import java.util.*

data object XMLtoShape : AssetSerializator<IMovableShape2D> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): IMovableShape2D {
		val v = manager.load<XMLElement>(key)
		val type = v.getAttribute("type").lowercase(Locale.getDefault())
		when(type) {
			"circle" -> {
				val radius = v.getChildren("radius").content.toFloat()
				return Circle2D(radius)
			}

			"capsule" -> {
			}
		}
		throw IllegalArgumentException("unsupported type $type")
	}
}
