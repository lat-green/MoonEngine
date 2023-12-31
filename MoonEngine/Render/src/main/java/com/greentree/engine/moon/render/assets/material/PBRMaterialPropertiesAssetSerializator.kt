package com.greentree.engine.moon.render.assets.material

import com.greentree.commons.graphics.smart.texture.Texture
import com.greentree.commons.image.Color
import com.greentree.commons.image.image.ColorImageData
import com.greentree.engine.moon.assets.Value6Function
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.DefaultAssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.key.ResultAssetKey
import com.greentree.engine.moon.assets.provider.AssetProvider
import com.greentree.engine.moon.assets.provider.map
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey
import com.greentree.engine.moon.render.assets.texture.Texture2DAssetKey
import com.greentree.engine.moon.render.texture.Texture2DType

class PBRMaterialPropertiesAssetSerializator : AssetSerializator<BaseDeferredMaterial> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): AssetProvider<BaseDeferredMaterial> {
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
		val albedoAsset = manager.load<Texture>(texture_albedo)
		val normalAsset = manager.load<Texture>(texture_normal)
		val metallicAsset = manager.load<Texture>(texture_metallic)
		val roughnessAsset = manager.load<Texture>(texture_roughness)
		val displacementAsset = manager.load<Texture>(texture_displacement)
		val ambient_occlusionAsset = manager.load<Texture>(texture_ambient_occlusion)
		return map(
			albedoAsset, normalAsset, metallicAsset, roughnessAsset, displacementAsset,
			ambient_occlusionAsset, TexturesToBaseDeferredMaterial
		)
	}

	private object TexturesToBaseDeferredMaterial :
		Value6Function<Texture, Texture, Texture, Texture, Texture, Texture, BaseDeferredMaterial> {

		override fun apply(
			albedo: Texture, normal: Texture, metallic: Texture, roughness: Texture,
			displacement: Texture, ambientOcclusion: Texture,
		): BaseDeferredMaterial {
			val properties = BaseDeferredMaterial()
			properties.put("material.albedo", albedo)
			properties.put("material.ao", ambientOcclusion)
			properties.put("material.displacement", displacement)
			properties.put("material.metallic", metallic)
			properties.put("material.normal", normal)
			properties.put("material.roughness", roughness)
			properties.put("ao_scale", 1f)
			properties.put("displacement_scale", .1f)
			return properties
		}
	}

	companion object {

		private val DEFAULT_ALBEDO: AssetKey = ResultAssetKey(ColorImageData(Color.white))
		private val DEFAULT_NORMAL: AssetKey = ResultAssetKey(ColorImageData(Color(0.5, 0.5, 1.0)))
		private val DEFAULT_METALLIC: AssetKey = ResultAssetKey(ColorImageData(Color.black))
		private val DEFAULT_ROUGHNESS: AssetKey = ResultAssetKey(ColorImageData(Color.black))
		private val DEFAULT_DISPLACEMENT: AssetKey = ResultAssetKey(ColorImageData(Color.black))
		private val DEFAULT_AMBIENT_OCCLUSION: AssetKey = ResultAssetKey(ColorImageData(Color.white))
	}
}