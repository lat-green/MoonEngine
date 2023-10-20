package com.greentree.engine.moon.base.assets.scene

import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value1Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import com.greentree.engine.moon.ecs.component.Component

object XMLComponentAssetSerializator : AssetSerializator<Component> {

	override fun load(manager: AssetManager, key: AssetKey): Asset<Component> {
		val res = manager.load<XMLElement>(key)
		return res.map(XMLToComponent)
	}

	object XMLToComponent : Value1Function<XMLElement, Component> {

		override fun apply(value: XMLElement): Component {

		}
	}
}