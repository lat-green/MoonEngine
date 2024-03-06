package com.greentree.engine.moon.render.assets

import com.greentree.engine.moon.assets.serializator.addSerializator
import com.greentree.engine.moon.base.AssetManagerProperty
import com.greentree.engine.moon.base.property.modules.WriteProperty
import com.greentree.engine.moon.modules.LaunchModule
import com.greentree.engine.moon.modules.property.EngineProperties
import com.greentree.engine.moon.render.assets.image.ColorImageAssetSerializator
import com.greentree.engine.moon.render.assets.image.ResourceImageAssetSerializator
import com.greentree.engine.moon.render.assets.image.cube.CubeImageAssetSerializator
import com.greentree.engine.moon.render.assets.image.cube.PropertiesCubeImageAssetSerializator
import com.greentree.engine.moon.render.assets.material.PBRMaterialAssetSerializator
import com.greentree.engine.moon.render.assets.material.PBRMaterialPropertiesAssetSerializator
import com.greentree.engine.moon.render.assets.shader.ShaderDataAssetSerializator
import com.greentree.engine.moon.render.assets.shader.ShaderProgramDataAssetSerializator
import com.greentree.engine.moon.render.assets.texture.Texture2DAssetSerializator
import com.greentree.engine.moon.render.assets.texture.cube.CubeTextureAssetSerializator

class RenderAssetSerializatorModule : LaunchModule {

	@WriteProperty(AssetManagerProperty::class)
	override fun launch(context: EngineProperties) {
		val manager = context.get(AssetManagerProperty::class.java).manager
		manager.addSerializator(CubeImageAssetSerializator)
		manager.addSerializator(PropertiesCubeImageAssetSerializator)
		manager.addSerializator(CubeTextureAssetSerializator)
		manager.addSerializator(ColorImageAssetSerializator)
		manager.addSerializator(ResourceImageAssetSerializator)
		manager.addSerializator(Texture2DAssetSerializator)
		manager.addSerializator(ShaderDataAssetSerializator)
		manager.addSerializator(ShaderProgramDataAssetSerializator)
		manager.addSerializator(PBRMaterialPropertiesAssetSerializator)
		manager.addSerializator(PBRMaterialAssetSerializator)
//		manager.addDefaultLoader(ImageDataDefaultLoader())
		//        manager.addDefaultLoader(new ColorImageDataDefaultLoader());
//        manager.addDefaultLoader(new ColorDefaultLoader());
	}
}
