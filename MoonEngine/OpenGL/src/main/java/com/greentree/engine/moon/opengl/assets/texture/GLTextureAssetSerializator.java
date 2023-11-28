package com.greentree.engine.moon.opengl.assets.texture;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.render.texture.Texture2DData;

public class GLTextureAssetSerializator implements AssetSerializator<GLTexture2DImpl> {

    @Override
    public Asset<GLTexture2DImpl> load(AssetLoader.Context manager, AssetKey key) {
        final var texture = manager.load(Texture2DData.class, key);
        return AssetKt.map(texture, new GLTextureAsset());
    }

    private static final class GLTextureAsset
            implements Value1Function<Texture2DData, GLTexture2DImpl> {

        private static final long serialVersionUID = 1L;

        @Override
        public GLTexture2DImpl apply(Texture2DData texture) {
            final var tex = new GLTexture2DImpl();
            return applyWithDest(texture, tex);
        }

        @Override
        public GLTexture2DImpl applyWithDest(Texture2DData texture, GLTexture2DImpl tex) {
            final var img = texture.image();
            tex.bind();
            tex.setData(GLPixelFormat.RGBA, img.getWidth(), img.getHeight(), GLPixelFormat.gl(img.getFormat()), img.getData());
            final var type = texture.type();
            tex.setMagFilter(GLEnums.get(type.filteringMag()));
            tex.setMagFilter(GLEnums.get(type.filteringMin()));
            tex.setWrapX(GLEnums.get(type.wrapX()));
            tex.setWrapY(GLEnums.get(type.wrapY()));
            tex.generateMipmap();
            tex.unbind();
            return tex;
        }

    }

}
