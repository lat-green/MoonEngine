package com.greentree.engine.moon.opengl.assets.shader;

import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.render.shader.ShaderData;

public class GLSLShaderAssetSerializator implements AssetSerializator<GLSLShader> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(ShaderData.class, key);
	}
	
	@Override
	public Value<GLSLShader> load(LoadContext manager, AssetKey key) {
		if(manager.canLoad(ShaderData.class, key)) {
			final var shader = manager.load(ShaderData.class, key);
			return manager.map(shader, new GLSLShaderAsset());
		}
		return null;
	}
	
	private static final class GLSLShaderAsset implements Value1Function<ShaderData, GLSLShader> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public GLSLShader apply(ShaderData s) {
			if(s == null)
				return null;
			return new GLSLShader(s.text(), GLEnums.get(s.type()));
		}
		
	}
	
	
}
