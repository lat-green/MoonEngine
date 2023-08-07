package com.greentree.engine.moon.opengl.assets.texture;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.opengl.adapter.TextureAddapter;

public class TextureAddapterAssetSerializator implements AssetSerializator<TextureAddapter> {

    @Override
    public Asset<TextureAddapter> load(AssetManager manager, AssetKey ckey) {
        final var texture = manager.load(GLTexture.class, ckey);
        return AssetKt.map(texture, new GLTexture_TextureAddapter_Function());
    }

    private static final class GLTexture_TextureAddapter_Function
            implements Value1Function<GLTexture, TextureAddapter> {

        private static final long serialVersionUID = 1L;

        @Override
        public TextureAddapter apply(GLTexture texture) {
            return new TextureAddapter(texture);
        }

    }

}
