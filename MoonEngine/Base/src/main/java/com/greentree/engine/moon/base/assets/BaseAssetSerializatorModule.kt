package com.greentree.engine.moon.base.assets

import com.greentree.engine.moon.assets.serializator.addSerializator
import com.greentree.engine.moon.base.AssetManagerProperty
import com.greentree.engine.moon.base.assets.number.BooleanAssetSerializator
import com.greentree.engine.moon.base.assets.number.ByteAssetSerializator
import com.greentree.engine.moon.base.assets.number.DoubleAssetSerializator
import com.greentree.engine.moon.base.assets.number.FloatAssetSerializator
import com.greentree.engine.moon.base.assets.number.IntAssetSerializator
import com.greentree.engine.moon.base.assets.number.LongAssetSerializator
import com.greentree.engine.moon.base.assets.number.ShortAssetSerializator
import com.greentree.engine.moon.base.assets.properties.PropertiesAssetSerializator
import com.greentree.engine.moon.base.assets.scene.EntityAssetSerializator
import com.greentree.engine.moon.base.assets.scene.EntityDefault
import com.greentree.engine.moon.base.assets.scene.XMLSceneAssetSerializator
import com.greentree.engine.moon.base.assets.text.PropertyStringAssetSerializator
import com.greentree.engine.moon.base.assets.text.RefStringBuilderAssetSerializator
import com.greentree.engine.moon.base.assets.text.RefStringBuilderInsertAssetSerializator
import com.greentree.engine.moon.base.assets.text.ResourceToTextSerializator
import com.greentree.engine.moon.base.assets.xml.ResourceToXMLAssetSerializator
import com.greentree.engine.moon.base.assets.xml.TextToXMLAssetSerializator
import com.greentree.engine.moon.base.assets.xml.XMLChildrenAssetSerializator
import com.greentree.engine.moon.base.property.modules.WriteProperty
import com.greentree.engine.moon.base.scene.SceneManagerProperty
import com.greentree.engine.moon.modules.LaunchModule
import com.greentree.engine.moon.modules.property.EngineProperties

class BaseAssetSerializatorModule : LaunchModule {

	@WriteProperty(AssetManagerProperty::class, SceneManagerProperty::class)
	override fun launch(context: EngineProperties) {
		val manager = context[AssetManagerProperty::class.java].manager
		manager.addDefaultLoader(EntityDefault)

		manager.addSerializator(ResourceToXMLAssetSerializator)
		manager.addSerializator(TextToXMLAssetSerializator)

		manager.addSerializator(ResourceToTextSerializator)
		manager.addSerializator(PropertyStringAssetSerializator)
		manager.addSerializator(RefStringBuilderInsertAssetSerializator)

		manager.addSerializator(PropertiesAssetSerializator)

		manager.addSerializator(XMLSceneAssetSerializator)

		manager.addSerializator(RefStringBuilderAssetSerializator)

		manager.addSerializator(XMLChildrenAssetSerializator)

		manager.addSerializator(EntityAssetSerializator)

		manager.addSerializator(LongAssetSerializator)
		manager.addSerializator(IntAssetSerializator)
		manager.addSerializator(DoubleAssetSerializator)
		manager.addSerializator(FloatAssetSerializator)
		manager.addSerializator(ByteAssetSerializator)
		manager.addSerializator(ShortAssetSerializator)
		manager.addSerializator(BooleanAssetSerializator)
	}
}
