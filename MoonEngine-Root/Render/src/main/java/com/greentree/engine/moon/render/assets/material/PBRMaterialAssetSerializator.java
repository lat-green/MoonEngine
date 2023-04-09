package com.greentree.engine.moon.render.assets.material;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value2Function;
import com.greentree.engine.moon.render.pipeline.material.Material;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

public final class PBRMaterialAssetSerializator implements AssetSerializator<Material> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(MaterialProperties.class, key);
	}
	
	@Override
	public Value<Material> load(LoadContext manager, AssetKey key) {
		if(manager.canLoad(MaterialProperties.class, key)) {
			var program = manager.load(ShaderProgramData.class, "pbr_mapping.glsl");
			var properties = manager.load(MaterialProperties.class, key);
			return manager.map(program, properties, new PBRMaterialAssetSerializatorFunction());
		}
		return null;
	}
	
	private static final class PBRMaterialAssetSerializatorFunction
			implements Value2Function<ShaderProgramData, MaterialProperties, Material> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Material apply(ShaderProgramData program, MaterialProperties properties) {
			return new Material(program, properties);
		}
		
	}
	
}
