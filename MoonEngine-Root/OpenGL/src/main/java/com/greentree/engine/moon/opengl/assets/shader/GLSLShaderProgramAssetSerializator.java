package com.greentree.engine.moon.opengl.assets.shader;

import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class GLSLShaderProgramAssetSerializator implements AssetSerializator<GLShaderProgram> {

    private static final TypeInfo<Iterable<GLSLShader>> TYPE = TypeInfoBuilder
            .getTypeInfo(Iterable.class, GLSLShader.class);

    @Override
    public Asset<GLShaderProgram> load(AssetManager manager, AssetKey key) {
        final var program = manager.load(TYPE, key);
        return AssetKt.map(program, new GLShaderProgramFunction());
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
