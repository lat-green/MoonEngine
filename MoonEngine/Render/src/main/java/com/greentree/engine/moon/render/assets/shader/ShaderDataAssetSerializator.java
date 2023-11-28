package com.greentree.engine.moon.render.assets.shader;

import com.greentree.engine.moon.assets.Value1Function;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader;
import com.greentree.engine.moon.render.shader.ShaderDataImpl;
import com.greentree.engine.moon.render.shader.ShaderLanguage;
import com.greentree.engine.moon.render.shader.ShaderType;

public class ShaderDataAssetSerializator implements AssetSerializator<ShaderDataImpl> {

    @Override
    public Asset<ShaderDataImpl> load(AssetLoader.Context manager, AssetKey ckey) {
        if (ckey instanceof ShaderAssetKey key) {
            final var text = manager.load(String.class, key.text());
            final var type = key.shaderType();
            final var language = key.language();
            return AssetKt.map(text, new TextToShaderDataFunction(type, language));
        }
        return null;
    }

    private record TextToShaderDataFunction(ShaderType type, ShaderLanguage language)
            implements Value1Function<String, ShaderDataImpl> {

        private static final long serialVersionUID = 1L;

        @Override
        public String toString() {
            return "TextToShaderData[type=" + type + ", language=" + language + ']';
        }

        @Override
        public ShaderDataImpl apply(String value) {
            if (value == null)
                return null;
            return new ShaderDataImpl(value, type, language);
        }

    }

}
