package com.greentree.engine.moon.opengl.assets.texture;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.texture.builder.TextureBuilder;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.render.texture.Texture2DData;

public class GLTextureAssetSerializator implements AssetSerializator<GLTexture2DImpl> {

    @Override
    public Asset<GLTexture2DImpl> load(AssetManager manager, AssetKey ckey) {
        final var texture = manager.load(Texture2DData.class, ckey);
        return AssetKt.map(texture, new GLTextureAsset());
    }

    private static final class GLTextureAsset
            implements Value1Function<Texture2DData, GLTexture2DImpl> {

        private static final long serialVersionUID = 1L;

        @Override
        public GLTexture2DImpl apply(Texture2DData texture) {
            final var img = texture.image();
            final var tex = TextureBuilder
                    .builder(img.getData(), GLPixelFormat.gl(img.getFormat()))
                    .build2d(img.getWidth(), img.getHeight(), GLPixelFormat.RGBA);
            tex.bind();
            final var type = texture.type();
            tex.setMagFilter(GLEnums.get(type.filteringMag()));
            tex.setMagFilter(GLEnums.get(type.filteringMin()));
            tex.setWrapX(GLEnums.get(type.wrapX()));
            tex.setWrapY(GLEnums.get(type.wrapY()));
            tex.unbind();
            return tex;
        }

    }

}
