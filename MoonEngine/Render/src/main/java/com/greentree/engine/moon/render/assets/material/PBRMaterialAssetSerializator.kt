package com.greentree.engine.moon.render.assets.material

import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.load
import com.greentree.engine.moon.assets.serializator.AssetSerializator

data object PBRMaterialAssetSerializator : AssetSerializator<Material> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Material {
		val shader = manager.load<Shader>("shader/pbr_mapping/pbr_mapping.glsl")
		val material = manager.load<DeferredMaterial>(key)
		return material.build(shader)
	}
}