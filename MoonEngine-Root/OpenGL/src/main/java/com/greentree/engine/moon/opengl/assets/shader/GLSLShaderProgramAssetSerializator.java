package com.greentree.engine.moon.opengl.assets.shader;

import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.commons.util.iterator.IteratorUtil;

public class GLSLShaderProgramAssetSerializator implements AssetSerializator<GLShaderProgram> {
	
	private static final TypeInfo<Iterable<GLSLShader>> TYPE = TypeInfoBuilder
			.getTypeInfo(Iterable.class, GLSLShader.class);
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(TYPE, key);
	}
	
	@Override
	public Value<GLShaderProgram> load(LoadContext manager, AssetKey ckey) {
		{
			final var key = manager.load(TYPE, ckey).toLazy();
			if(key != null)
				return manager.map(key, new GLShaderProgramFunction());
		}
		return null;
	}
	
	private static final class GLShaderProgramFunction
			implements Value1Function<Iterable<GLSLShader>, GLShaderProgram> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public GLShaderProgram apply(Iterable<GLSLShader> data) {
			return new GLShaderProgram(IteratorUtil.filter(data, x->x != null));
		}
		
		@Override
		public GLShaderProgram applyWithDest(Iterable<GLSLShader> data, GLShaderProgram program) {
			final var p = new GLShaderProgram(IteratorUtil.filter(data, x->x != null));
			program.close();
			return p;
		}
		
	}
	
}
