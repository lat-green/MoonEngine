package com.greentree.engine.moon.opengl.assets.texture;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.render.texture.CubeTextureData;

public class GLCubeTextureAssetSerializator implements AssetSerializator<GLCubeMapTexture> {

    @Override
    public Asset<GLCubeMapTexture> load(AssetManager manager, AssetKey key) {
        final var texture = manager.load(CubeTextureData.class, key);
        return AssetKt.map(texture, new GLCubeMapAsset());
    }

    public class GLCubeMapAsset implements Value1Function<CubeTextureData, GLCubeMapTexture> {

        private static final long serialVersionUID = 1L;

        @Override
        public GLCubeMapTexture apply(CubeTextureData texture) {
            final var tex = new GLCubeMapTexture();
            return applyWithDest(texture, tex);
        }

        @Override
        public GLCubeMapTexture applyWithDest(CubeTextureData texture, GLCubeMapTexture tex) {
            tex.bind();
            final var iter = texture.image().iterator();
            for (var d : tex.dirs()) {
                final var image = iter.next();
                d.setData(GLPixelFormat.RGB, image.getWidth(), image.getHeight(),
                        GLPixelFormat.gl(image.getFormat()), image.getData());
            }
            final var type = texture.type();
            tex.setMagFilter(GLEnums.get(type.filteringMag()));
            tex.setMagFilter(GLEnums.get(type.filteringMin()));
            tex.setWrapX(GLEnums.get(type.wrapX()));
            tex.setWrapY(GLEnums.get(type.wrapY()));
            tex.setWrapZ(GLEnums.get(type.wrapZ()));
            tex.generateMipmap();
            tex.unbind();
            return tex;
        }

    }

}
