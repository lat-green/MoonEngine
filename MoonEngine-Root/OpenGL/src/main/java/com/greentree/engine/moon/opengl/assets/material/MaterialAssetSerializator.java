package com.greentree.engine.moon.opengl.assets.material;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value2Function;
import com.greentree.engine.moon.opengl.adapter.TextureAddapter;
import com.greentree.engine.moon.render.pipeline.material.Material;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesBase;
import com.greentree.engine.moon.render.shader.ShaderProgramData;


public class MaterialAssetSerializator implements AssetSerializator<Material> {
	
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(GLPBRMaterial.class, key);
	}
	
	@Override
	public Value<Material> load(LoadContext manager, AssetKey key) {
		if(manager.canLoad(GLPBRMaterial.class, key)) {
			final var program = manager.load(ShaderProgramData.class, "pbr_mapping.glsl");
			final var material = manager.load(GLPBRMaterial.class, key);
			return manager.map(program, material, new MaterialRebuildAssetSerializator());
		}
		return null;
	}
	
	
	private static final class MaterialRebuildAssetSerializator
			implements Value2Function<ShaderProgramData, GLPBRMaterial, Material> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Material apply(ShaderProgramData program, GLPBRMaterial material) {
			final var ps = new MaterialPropertiesBase();
			
			ps.put("material.albedo", new TextureAddapter(material.albedo()));
			ps.put("material.ao", new TextureAddapter(material.ambientOcclusion()));
			ps.put("material.displacement", new TextureAddapter(material.displacement()));
			ps.put("material.metallic", new TextureAddapter(material.metallic()));
			ps.put("material.normal", new TextureAddapter(material.normal()));
			ps.put("material.roughness", new TextureAddapter(material.roughness()));
			
			ps.put("ao_scale", 1f);
			ps.put("displacement_scale", .1f);
			
			return new Material(program, ps);
		}
		
	}
	
}
