package com.greentree.engine.moon.script

import com.greentree.engine.moon.base.AssetManagerProperty
import com.greentree.engine.moon.base.assets
import com.greentree.engine.moon.base.property.modules.WriteProperty
import com.greentree.engine.moon.modules.LaunchModule
import com.greentree.engine.moon.modules.property.EngineProperties
import com.greentree.engine.moon.script.assets.ScriptAssetSerializator

class ScriptsModule : LaunchModule {

	@WriteProperty(AssetManagerProperty::class)
	override fun launch(context: EngineProperties) {
		val manager = context.assets
		manager.addSerializator(ScriptAssetSerializator)
	}
}
