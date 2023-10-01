package com.greentree.engine.moon.render.assets.material

import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.asset.Value2Function
import com.greentree.engine.moon.assets.asset.map
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.manager.AssetManager
import com.greentree.engine.moon.assets.serializator.manager.load

class PBRMaterialAssetSerializator : AssetSerializator<Material> {

	override fun load(manager: AssetManager, key: AssetKey): Asset<Material> {
		val program = manager.load<Shader>("shader/pbr_mapping/pbr_mapping.glsl")
		val properties = manager.load<DeferredMaterial>(key)
		return map(
			program,
			properties,
			PBRMaterialAssetSerializatorFunction
		)
	}

	private object PBRMaterialAssetSerializatorFunction :
		Value2Function<Shader, DeferredMaterial, Material> {

		override fun apply(shader: Shader, material: DeferredMaterial): Material {
			return material.build(shader)
		}
	}
}