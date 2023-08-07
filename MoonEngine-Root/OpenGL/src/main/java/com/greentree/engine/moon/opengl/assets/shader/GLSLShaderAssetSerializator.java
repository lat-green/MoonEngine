package com.greentree.engine.moon.opengl.assets.shader;

import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.render.shader.ShaderData;

public class GLSLShaderAssetSerializator implements AssetSerializator<GLSLShader> {

    @Override
    public Asset<GLSLShader> load(AssetManager manager, AssetKey key) {
        final var shader = manager.load(ShaderData.class, key);
        return AssetKt.map(shader, new GLSLShaderAsset());
    }

    private static final class GLSLShaderAsset implements Value1Function<ShaderData, GLSLShader> {

        private static final long serialVersionUID = 1L;

        @Override
        public GLSLShader apply(ShaderData s) {
            if (s == null)
                return null;
            return new GLSLShader(s.text(), GLEnums.get(s.type()));
        }

    }

}
