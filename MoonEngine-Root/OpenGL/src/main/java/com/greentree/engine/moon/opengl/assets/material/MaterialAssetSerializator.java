package com.greentree.engine.moon.opengl.assets.material;

import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value2Function;
import com.greentree.engine.moon.opengl.adapter.OpenGLShaderProgramAddpter;
import com.greentree.engine.moon.opengl.material.OpenGLMaterialProperties;
import com.greentree.engine.moon.opengl.material.OpenGLMaterialPropertiesImpl;
import com.greentree.engine.moon.render.pipeline.material.Material;


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
			final var ps = new OpenGLMaterialPropertiesImpl();
			
			ps.put("material.albedo", material.albedo());
			ps.put("material.ao", material.ambientOcclusion());
			ps.put("material.displacement", material.displacement());
			ps.put("material.metallic", material.metallic());
			ps.put("material.normal", material.normal());
			ps.put("material.roughness", material.roughness());
			
			ps.put("ao_scale", 1f);
			ps.put("height_scale", 1f);
			
			return new Material(new OpenGLShaderProgramAddpter(program), ps);
		}
		
	}
	
}
