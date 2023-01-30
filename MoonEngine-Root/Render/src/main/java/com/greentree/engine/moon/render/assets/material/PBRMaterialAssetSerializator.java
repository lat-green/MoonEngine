package com.greentree.engine.moon.render.assets.material;

import java.util.Properties;

import com.greentree.common.renderer.texture.Texture2DType;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.DefaultAssetKey;
import com.greentree.commons.assets.key.ResourceAssetKey;
import com.greentree.commons.assets.key.ResultAssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value6Function;
import com.greentree.commons.image.Color;
import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey;
import com.greentree.engine.moon.render.assets.texture.Texture2DAssetKey;

public final class PBRMaterialAssetSerializator<T> implements AssetSerializator<PBRMaterial<T>> {
	
	private final static AssetKey DEFAULT_ALBEDO = new ResultAssetKey(new ImageData(Color.white));
	
	private final static AssetKey DEFAULT_NORMAL = new ResultAssetKey(
			new ImageData(new Color(0.5, 0.5, 1)));
	
	private final static AssetKey DEFAULT_METALLIC = new ResultAssetKey(new ImageData(Color.black));
	
	private final static AssetKey DEFAULT_ROUGHNESS = new ResultAssetKey(
			new ImageData(Color.black));
	
	private final static AssetKey DEFAULT_DISPLACEMENT = new ResultAssetKey(
			new ImageData(Color.black));
	private final static AssetKey DEFAULT_AMBIENT_OCCLUSION = new ResultAssetKey(
			new ImageData(Color.white));
	
	private final TypeInfo<T> TYPE;
	
	
	public PBRMaterialAssetSerializator(Class<T> cls) {
		this(TypeInfoBuilder.getTypeInfo(cls));
	}
	
	public PBRMaterialAssetSerializator(TypeInfo<T> type) {
		TYPE = type;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Properties.class, key);
	}
	
	@Override
	public TypeInfo<PBRMaterial<T>> getType() {
		return TypeInfoBuilder.getTypeInfo(PBRMaterial.class, TYPE);
	}
	
	@Override
	public Value<PBRMaterial<T>> load(LoadContext manager, AssetKey key) {
		if(manager.canLoad(Properties.class, key)) {
			final var albedo = new DefaultAssetKey(
					new ResourceAssetKey(new PropertyAssetKey(key, "texture.albedo")),
					DEFAULT_ALBEDO);
			final var normal = new DefaultAssetKey(
					new ResourceAssetKey(new PropertyAssetKey(key, "texture.normal")),
					DEFAULT_NORMAL);
			final var metallic = new DefaultAssetKey(
					new ResourceAssetKey(new PropertyAssetKey(key, "texture.metallic")),
					DEFAULT_METALLIC);
			final var roughness = new DefaultAssetKey(
					new ResourceAssetKey(new PropertyAssetKey(key, "texture.roughness")),
					DEFAULT_ROUGHNESS);
			final var displacement = new DefaultAssetKey(
					new ResourceAssetKey(new PropertyAssetKey(key, "texture.displacement")),
					DEFAULT_DISPLACEMENT);
			final var ambient_occlusion = new DefaultAssetKey(
					new ResourceAssetKey(new PropertyAssetKey(key, "texture.ambient_occlusion")),
					DEFAULT_AMBIENT_OCCLUSION);
			
			final var texture_albedo = new Texture2DAssetKey(albedo, new Texture2DType());
			final var texture_normal = new Texture2DAssetKey(normal, new Texture2DType());
			final var texture_metallic = new Texture2DAssetKey(metallic, new Texture2DType());
			final var texture_roughness = new Texture2DAssetKey(roughness, new Texture2DType());
			final var texture_displacement = new Texture2DAssetKey(displacement,
					new Texture2DType());
			final var texture_ambient_occlusion = new Texture2DAssetKey(ambient_occlusion,
					new Texture2DType());
			
			final Value<T> albedoAsset, normalAsset, metallicAsset, roughnessAsset,
					displacementAsset, ambient_occlusionAsset;
			
			//			try(final var parallel = manager.parallel()) {
			//    			albedoAsset = parallel.load(TYPE, texture_albedo);
			//    			normalAsset = parallel.load(TYPE, texture_normal);
			//    			metallicAsset = parallel.load(TYPE, texture_metallic);
			//    			roughnessAsset = parallel.load(TYPE, texture_roughness);
			//    			displacementAsset = parallel.load(TYPE, texture_displacement);
			//    			ambient_occlusionAsset = parallel.load(TYPE, texture_ambient_occlusion);
			//			}
			
			albedoAsset = manager.load(TYPE, texture_albedo);
			normalAsset = manager.load(TYPE, texture_normal);
			metallicAsset = manager.load(TYPE, texture_metallic);
			roughnessAsset = manager.load(TYPE, texture_roughness);
			displacementAsset = manager.load(TYPE, texture_displacement);
			ambient_occlusionAsset = manager.load(TYPE, texture_ambient_occlusion);
			
			return manager.map(albedoAsset, normalAsset, metallicAsset, roughnessAsset,
					displacementAsset, ambient_occlusionAsset,
					new PBRMaterialPropertiesAssetFunction<>());
		}
		return null;
	}
	
	//	@Override TODO
	//	public PBRMaterial<T> loadDefault(LoadContext context) {
	//		final var manager = context.manager();
	//
	//		final var albedo = manager.load(TYPE, DEFAULT_ALBEDO);
	//
	//		return new PBRMaterial<>(DEFAULT_ALBEDO, DEFAULT_NORMAL, DEFAULT_METALLIC, DEFAULT_ROUGHNESS, DEFAULT_DISPLACEMENT, DEFAULT_AMBIENT_OCCLUSION);
	//	}
	
	private final static class PBRMaterialPropertiesAssetFunction<T>
			implements Value6Function<T, T, T, T, T, T, PBRMaterial<T>> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public PBRMaterial<T> apply(T t1, T t2, T t3, T t4, T t5, T t6) {
			return new PBRMaterial<>(t1, t2, t3, t4, t5, t6);
		}
		
	}
	
}
