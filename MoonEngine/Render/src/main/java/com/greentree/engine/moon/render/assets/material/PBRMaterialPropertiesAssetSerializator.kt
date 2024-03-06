package com.greentree.engine.moon.render.assets.material

import com.greentree.commons.graphics.smart.texture.Texture
import com.greentree.commons.image.Color
import com.greentree.commons.image.image.ColorImageData
import com.greentree.engine.moon.assets.getValue
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.DefaultAssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.loader.AssetLoader
import com.greentree.engine.moon.assets.loader.loadAsset
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey
import com.greentree.engine.moon.render.assets.texture.Texture2DAssetKey
import com.greentree.engine.moon.render.texture.Texture2DType

data object PBRMaterialPropertiesAssetSerializator : AssetSerializator<BaseDeferredMaterial> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): BaseDeferredMaterial {
		val albedo = DefaultAssetKey(ResourceAssetKey(PropertyAssetKey(key, "texture.albedo")), DEFAULT_ALBEDO)
//		val albedo = ResourceAssetKey(PropertyAssetKey(key, "texture.albedo"))
		val normal = DefaultAssetKey(ResourceAssetKey(PropertyAssetKey(key, "texture.normal")), DEFAULT_NORMAL)
		val metallic = DefaultAssetKey(ResourceAssetKey(PropertyAssetKey(key, "texture.metallic")), DEFAULT_METALLIC)
		val roughness = DefaultAssetKey(ResourceAssetKey(PropertyAssetKey(key, "texture.roughness")), DEFAULT_ROUGHNESS)
		val displacement =
			DefaultAssetKey(ResourceAssetKey(PropertyAssetKey(key, "texture.displacement")), DEFAULT_DISPLACEMENT)
		val ambient_occlusion = DefaultAssetKey(
			ResourceAssetKey(PropertyAssetKey(key, "texture.ambient_occlusion")),
			DEFAULT_AMBIENT_OCCLUSION
		)
		val texture_albedo = Texture2DAssetKey(albedo, Texture2DType())
		val texture_normal = Texture2DAssetKey(normal, Texture2DType())
		val texture_metallic = Texture2DAssetKey(metallic, Texture2DType())
		val texture_roughness = Texture2DAssetKey(roughness, Texture2DType())
		val texture_displacement = Texture2DAssetKey(displacement, Texture2DType())
		val texture_ambient_occlusion = Texture2DAssetKey(ambient_occlusion, Texture2DType())
		val albedoAsset by manager.loadAsset<Texture>(texture_albedo)
		val normalAsset by manager.loadAsset<Texture>(texture_normal)
		val metallicAsset by manager.loadAsset<Texture>(texture_metallic)
		val roughnessAsset by manager.loadAsset<Texture>(texture_roughness)
		val displacementAsset by manager.loadAsset<Texture>(texture_displacement)
		val ambient_occlusionAsset by manager.loadAsset<Texture>(texture_ambient_occlusion)
		val properties = BaseDeferredMaterial()
		properties.put("material.albedo", albedoAsset)
		properties.put("material.ao", ambient_occlusionAsset)
		properties.put("material.displacement", displacementAsset)
		properties.put("material.metallic", metallicAsset)
		properties.put("material.normal", normalAsset)
		properties.put("material.roughness", roughnessAsset)
		properties.put("ao_scale", 1f)
		properties.put("displacement_scale", .1f)
		return properties
	}

	private val DEFAULT_ALBEDO: AssetKey = ResultAssetKey(ColorImageData(Color.white))
	private val DEFAULT_NORMAL: AssetKey = ResultAssetKey(ColorImageData(Color(0.5, 0.5, 1.0)))
	private val DEFAULT_METALLIC: AssetKey = ResultAssetKey(ColorImageData(Color.black))
	private val DEFAULT_ROUGHNESS: AssetKey = ResultAssetKey(ColorImageData(Color.black))
	private val DEFAULT_DISPLACEMENT: AssetKey = ResultAssetKey(ColorImageData(Color.black))
	private val DEFAULT_AMBIENT_OCCLUSION: AssetKey = ResultAssetKey(ColorImageData(Color.white))
}