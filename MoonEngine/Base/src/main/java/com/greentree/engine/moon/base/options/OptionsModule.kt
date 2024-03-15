package com.greentree.engine.moon.base.options

import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.base.AssetManagerProperty
import com.greentree.engine.moon.base.property.modules.CreateProperty
import com.greentree.engine.moon.base.property.modules.ReadProperty
import com.greentree.engine.moon.modules.LaunchModule
import com.greentree.engine.moon.modules.property.EngineProperties
import java.util.*

class OptionsModule : LaunchModule {

	@ReadProperty(AssetManagerProperty::class)
	@CreateProperty(OptionsProperty::class)
	override fun launch(properties: EngineProperties) {
		val manager = properties.get(AssetManagerProperty::class.java).manager
		val ps = manager.load<Properties>("options.properties")
		properties.add(OptionsProperty(PropertiesOptionsProvider(ps)))
	}

	private data class PropertiesOptionsProvider(val properties: Properties) : OptionsProvider {

		override fun get(name: String): String {
			return properties.getProperty(name)
		}
	}
}
