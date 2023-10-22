package com.greentree.engine.moon.opengl.assets.shader;

import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

import java.util.ArrayList;

public class GLSLShaderProgramAssetSerializator implements AssetSerializator<GLShaderProgram> {

    @Override
    public Asset<GLShaderProgram> load(AssetManager manager, AssetKey key) {
        final var program = manager.load(ShaderProgramData.class, key);
        return AssetKt.map(program, new GLShaderProgramFunction());
    }

    private static final class GLShaderProgramFunction
            implements Value1Function<ShaderProgramData, GLShaderProgram> {

        private static final long serialVersionUID = 1L;

        @Override
        public GLShaderProgram apply(ShaderProgramData data) {
            var shaders = new ArrayList<GLSLShader>(5);
            for (var s : data)
                shaders.add(new GLSLShader(s.text(), GLEnums.get(s.type())));
            shaders.trimToSize();
            return new GLShaderProgram(shaders);
        }

    }

}
