package com.greentree.engine.moon.opengl.assets.shader;

import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.engine.moon.assets.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.provider.AssetProvider;
import com.greentree.engine.moon.assets.provider.AssetProviderKt;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.render.shader.ShaderData;

public class GLSLShaderAssetSerializator implements AssetSerializator<GLSLShader> {

    @Override
    public AssetProvider<GLSLShader> load(AssetLoader.Context manager, AssetKey key) {
        final var shader = manager.load(ShaderData.class, key);
        return AssetProviderKt.map(shader, new GLSLShaderAsset());
    }

    private static final class GLSLShaderAsset implements Value1Function<ShaderData, GLSLShader> {

        private static final long serialVersionUID = 1L;

        @Override
        public GLSLShader apply(ShaderData s) {
            return new GLSLShader(s.text(), GLEnums.get(s.type()));
        }

    }

}
