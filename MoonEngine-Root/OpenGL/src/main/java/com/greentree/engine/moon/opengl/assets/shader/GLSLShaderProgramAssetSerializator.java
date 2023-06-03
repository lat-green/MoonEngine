package com.greentree.engine.moon.opengl.assets.shader;

import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;

public class GLSLShaderProgramAssetSerializator implements AssetSerializator<GLShaderProgram> {

    private static final TypeInfo<Iterable<GLSLShader>> TYPE = TypeInfoBuilder
            .getTypeInfo(Iterable.class, GLSLShader.class);

    @Override
    public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
        return manager.canLoad(TYPE, key);
    }

    @Override
    public Value<GLShaderProgram> load(LoadContext manager, AssetKey key) {
        if (manager.canLoad(TYPE, key)) {
            final var program = manager.load(TYPE, key);
            return manager.map(program, new GLShaderProgramFunction());
        }
        return null;
    }

    private static final class GLShaderProgramFunction
            implements Value1Function<Iterable<GLSLShader>, GLShaderProgram> {

        private static final long serialVersionUID = 1L;

        @Override
        public GLShaderProgram apply(Iterable<GLSLShader> data) {
            return new GLShaderProgram(IteratorUtil.filter(data, x -> x != null));
        }

    }

}
