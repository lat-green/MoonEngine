package com.greentree.engine.moon.render.assets.shader;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.render.shader.ShaderDataImpl;
import com.greentree.engine.moon.render.shader.ShaderLanguage;
import com.greentree.engine.moon.render.shader.ShaderType;

public class ShaderDataAssetSerializator implements AssetSerializator<ShaderDataImpl> {

    @Override
    public boolean canLoad(AssetManager manager, AssetKey ckey) {
        if (ckey instanceof ShaderAssetKey key) {
            return manager.canLoad(String.class, key.text());
        }
        return false;
    }

    @Override
    public Asset<ShaderDataImpl> load(AssetManager manager, AssetKey ckey) {
        if (ckey instanceof ShaderAssetKey key) {
            final var text = manager.load(String.class, key.text());
            final var type = key.shaderType();
            final var language = key.language();
            return AssetKt.map(text, new ShaderDataImplFunction(type, language));
        }
        return null;
    }

    private static final class ShaderDataImplFunction
            implements Value1Function<String, ShaderDataImpl> {

        private static final long serialVersionUID = 1L;
        private final ShaderType type;
        private final ShaderLanguage language;

        public ShaderDataImplFunction(ShaderType type, ShaderLanguage language) {
            this.type = type;
            this.language = language;
        }

        @Override
        public ShaderDataImpl apply(String value) {
            if (value == null)
                return null;
            return new ShaderDataImpl(value, type, language);
        }

    }

}
