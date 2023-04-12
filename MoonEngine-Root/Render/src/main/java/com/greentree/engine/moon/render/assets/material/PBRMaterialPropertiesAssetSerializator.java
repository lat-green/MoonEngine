package com.greentree.engine.moon.render.assets.material;

import java.util.Properties;

import com.greentree.commons.image.Color;
import com.greentree.commons.image.image.ImageData;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.AssetKeyType;
import com.greentree.engine.moon.assets.key.DefaultAssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.serializator.manager.DefaultAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value6Function;
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey;
import com.greentree.engine.moon.render.assets.texture.Texture2DAssetKey;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesBase;
import com.greentree.engine.moon.render.pipeline.material.Property;
import com.greentree.engine.moon.render.texture.Texture2DType;

public final class PBRMaterialPropertiesAssetSerializator implements AssetSerializator<MaterialProperties> {
	
	private final static AssetKey DEFAULT_ALBEDO = new ResultAssetKey(new ImageData(Color.white));
	
	private final static AssetKey DEFAULT_NORMAL = new ResultAssetKey(new ImageData(new Color(0.5, 0.5, 1)));
	
	private final static AssetKey DEFAULT_METALLIC = new ResultAssetKey(new ImageData(Color.black));
	
	private final static AssetKey DEFAULT_ROUGHNESS = new ResultAssetKey(new ImageData(Color.black));
	
	private final static AssetKey DEFAULT_DISPLACEMENT = new ResultAssetKey(new ImageData(Color.black));
	
	private final static AssetKey DEFAULT_AMBIENT_OCCLUSION = new ResultAssetKey(new ImageData(Color.white));
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Properties.class, key);
	}
	
	@Override
	public Value<MaterialProperties> load(LoadContext manager, AssetKey key) {
		if(manager.canLoad(Properties.class, key)) {
			final var albedo = new DefaultAssetKey(new ResourceAssetKey(new PropertyAssetKey(key, "texture.albedo")),
					DEFAULT_ALBEDO);
			final var normal = new DefaultAssetKey(new ResourceAssetKey(new PropertyAssetKey(key, "texture.normal")),
					DEFAULT_NORMAL);
			final var metallic = new DefaultAssetKey(
					new ResourceAssetKey(new PropertyAssetKey(key, "texture.metallic")), DEFAULT_METALLIC);
			final var roughness = new DefaultAssetKey(
					new ResourceAssetKey(new PropertyAssetKey(key, "texture.roughness")), DEFAULT_ROUGHNESS);
			final var displacement = new DefaultAssetKey(
					new ResourceAssetKey(new PropertyAssetKey(key, "texture.displacement")), DEFAULT_DISPLACEMENT);
			final var ambient_occlusion = new DefaultAssetKey(
					new ResourceAssetKey(new PropertyAssetKey(key, "texture.ambient_occlusion")),
					DEFAULT_AMBIENT_OCCLUSION);
			
			final var texture_albedo = new Texture2DAssetKey(albedo, new Texture2DType());
			final var texture_normal = new Texture2DAssetKey(normal, new Texture2DType());
			final var texture_metallic = new Texture2DAssetKey(metallic, new Texture2DType());
			final var texture_roughness = new Texture2DAssetKey(roughness, new Texture2DType());
			final var texture_displacement = new Texture2DAssetKey(displacement, new Texture2DType());
			final var texture_ambient_occlusion = new Texture2DAssetKey(ambient_occlusion, new Texture2DType());
			
			final Value<Property> albedoAsset, normalAsset, metallicAsset, roughnessAsset, displacementAsset,
			ambient_occlusionAsset;
			
			//			try(final var parallel = manager.parallel()) {
			//    			albedoAsset = parallel.load(Property.class, texture_albedo);
			//    			normalAsset = parallel.load(Property.class, texture_normal);
			//    			metallicAsset = parallel.load(Property.class, texture_metallic);
			//    			roughnessAsset = parallel.load(Property.class, texture_roughness);
			//    			displacementAsset = parallel.load(Property.class, texture_displacement);
			//    			ambient_occlusionAsset = parallel.load(Property.class, texture_ambient_occlusion);
			//			}
			
			albedoAsset = manager.load(Property.class, texture_albedo);
			normalAsset = manager.load(Property.class, texture_normal);
			metallicAsset = manager.load(Property.class, texture_metallic);
			roughnessAsset = manager.load(Property.class, texture_roughness);
			displacementAsset = manager.load(Property.class, texture_displacement);
			ambient_occlusionAsset = manager.load(Property.class, texture_ambient_occlusion);
			
			return manager.map(albedoAsset, normalAsset, metallicAsset, roughnessAsset, displacementAsset,
					ambient_occlusionAsset, new MaterialPropertiesAssetSerializatorFunction());
		}
		return null;
	}
	
	
	@Override
	public MaterialProperties loadDefault(DefaultAssetManager manager, AssetKeyType type) {
		return new MaterialPropertiesBase();
	}
	
	private final static class MaterialPropertiesAssetSerializatorFunction
	implements Value6Function<Property, Property, Property, Property, Property, Property, MaterialProperties> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public MaterialProperties apply(Property albedo, Property normal, Property metallic, Property roughness,
				Property displacement, Property ambientOcclusion) {
			var properties = new MaterialPropertiesBase();
			
			properties.put("material.albedo", albedo);
			properties.put("material.ao", ambientOcclusion);
			properties.put("material.displacement", displacement);
			properties.put("material.metallic", metallic);
			properties.put("material.normal", normal);
			properties.put("material.roughness", roughness);

			properties.put("ao_scale", 1f);
			properties.put("displacement_scale", .1f);
			
			return properties;
		}
		
		
	}
}