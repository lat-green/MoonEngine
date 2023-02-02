package com.greentree.engine.moon.opengl.assets.shader;

import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.opengl.adapter.ShaderAddapter;

public final class ShaderAddapterAssetSerializator implements AssetSerializator<ShaderAddapter> {
	
	
	private static final class GLShaderProgram_ShaderAddapter_Function
			implements Value1Function<GLShaderProgram, ShaderAddapter> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public ShaderAddapter apply(GLShaderProgram value) {
			return new ShaderAddapter(value);
		}
		
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(GLShaderProgram.class, key);
	}
	
	@Override
	public Value<ShaderAddapter> load(LoadContext context, AssetKey key) {
		if(context.canLoad(GLShaderProgram.class, key)) {
			final var program = context.load(GLShaderProgram.class, key);
			return context.map(program, new GLShaderProgram_ShaderAddapter_Function());
		}
		return null;
	}
	
}
