package com.greentree.engine.moon.opengl.assets.material;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.render.assets.material.PBRMaterial;


public class GLPBRMaterialAssetSerializator implements AssetSerializator<GLPBRMaterial> {
	
	private static final TypeInfo<PBRMaterial<GLTexture2DImpl>> TYPE = TypeInfoBuilder
			.getTypeInfo(PBRMaterial.class, GLTexture2DImpl.class);
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(TYPE, key);
	}
	
	@Override
	public Value<GLPBRMaterial> load(LoadContext manager, AssetKey ckey) {
		{
			final var texture = manager.load(TYPE, ckey);
			if(texture != null)
				return manager.map(texture, new GLPBRMaterialAssetFunction());
		}
		return null;
	}
	
	public static final class GLPBRMaterialAssetFunction
			implements Value1Function<PBRMaterial<GLTexture2DImpl>, GLPBRMaterial> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public GLPBRMaterial apply(PBRMaterial<GLTexture2DImpl> material) {
			return new GLPBRMaterial(material.albedo(), material.normal(), material.metallic(),
					material.roughness(), material.displacement(), material.ambientOcclusion());
		}
		
	}
	
	
}
