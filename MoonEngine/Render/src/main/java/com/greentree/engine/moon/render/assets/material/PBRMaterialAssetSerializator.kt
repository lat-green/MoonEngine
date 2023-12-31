package com.greentree.engine.moon.render.assets.material

import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.engine.moon.assets.Value2Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load

class PBRMaterialAssetSerializator : AssetSerializator<Material> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): AssetProvider<Material> {
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