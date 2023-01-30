package com.greentree.engine.moon.opengl.assets.material;

import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.common.renderer.material.Material;
import com.greentree.common.renderer.material.MaterialPropertiesImpl;
import com.greentree.common.renderer.opengl.material.GLTextureProperty;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value2Function;
import com.greentree.engine.moon.opengl.render.material.GLPBRMaterial;


public class MaterialAssetSerializator implements AssetSerializator<Material> {
	
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(GLPBRMaterial.class, key);
	}
	
	@Override
	public Value<Material> load(LoadContext manager, AssetKey key) {
		if(manager.canLoad(GLPBRMaterial.class, key)) {
			final var program = manager.load(GLShaderProgram.class, "pbr_mapping.glsl");
			final var material = manager.load(GLPBRMaterial.class, key);
			return manager.map(program, material, new MaterialRebuildAssetSerializator());
		}
		return null;
	}
	
	
	private static final class MaterialRebuildAssetSerializator
			implements Value2Function<GLShaderProgram, GLPBRMaterial, Material> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Material apply(GLShaderProgram program, GLPBRMaterial material) {
			final var ps = new MaterialPropertiesImpl();
			
			ps.put("material.albedo", new GLTextureProperty(material.albedo()));
			ps.put("material.ao", new GLTextureProperty(material.ambientOcclusion()));
			ps.put("material.displacement", new GLTextureProperty(material.displacement()));
			ps.put("material.metallic", new GLTextureProperty(material.metallic()));
			ps.put("material.normal", new GLTextureProperty(material.normal()));
			ps.put("material.roughness", new GLTextureProperty(material.roughness()));
			
			ps.put("ao_scale", 1f);
			ps.put("height_scale", 1f);
			
			return new Material(ps, program);
		}
		
	}
	
}
