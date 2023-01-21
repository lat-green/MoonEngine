package com.greentree.engine.moon.opengl.assets.shader;

import com.greentree.common.graphics.sgl.GLEnums;
import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.renderer.shader.data.ShaderData;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;

public class GLSLShaderAssetSerializator implements AssetSerializator<GLSLShader> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(ShaderData.class, key);
	}
	
	@Override
	public Value<GLSLShader> load(LoadContext manager, AssetKey key) {
		if(manager.canLoad(ShaderData.class, key)) {
			final var shader = manager.load(ShaderData.class, key).toLazy();
			return manager.map(shader, new GLSLShaderAsset());
		}
		return null;
	}
	
	private static final class GLSLShaderAsset implements Value1Function<ShaderData, GLSLShader> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public GLSLShader applyWithDest(ShaderData s, GLSLShader dest) {
			if(s == null)
				return null;
			final var sh = new GLSLShader(s.text(), GLEnums.get(s.type()));
			dest.close();
			return sh;
		}
		
		@Override
		public GLSLShader apply(ShaderData s) {
			if(s == null)
				return null;
			return new GLSLShader(s.text(), GLEnums.get(s.type()));
		}
		
	}
	
	
}
