package com.greentree.engine.moon.render.assets.material

import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value2Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load
import com.greentree.engine.moon.render.material.Material
import com.greentree.engine.moon.render.material.MaterialProperties
import com.greentree.engine.moon.render.shader.ShaderProgramData

class PBRMaterialAssetSerializator : AssetSerializator<Material> {

	override fun load(manager: AssetManager, key: AssetKey): Asset<Material> {
		val program = manager.load<ShaderProgramData>("shader/pbr_mapping/pbr_mapping.glsl")
		val properties = manager.load<MaterialProperties>(key)
		return map(
			program,
			properties,
			PBRMaterialAssetSerializatorFunction
		)
	}

	private object PBRMaterialAssetSerializatorFunction :
		Value2Function<ShaderProgramData, MaterialProperties, Material> {

		override fun apply(program: ShaderProgramData, properties: MaterialProperties): Material {
			return Material(program, properties)
		}
	}
}