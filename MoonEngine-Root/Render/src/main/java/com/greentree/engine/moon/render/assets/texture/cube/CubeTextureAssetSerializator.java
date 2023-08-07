package com.greentree.engine.moon.render.assets.texture.cube;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.render.texture.CubeImageData;
import com.greentree.engine.moon.render.texture.CubeTextureData;
import com.greentree.engine.moon.render.texture.Texture3DType;

import java.util.Objects;

public class CubeTextureAssetSerializator implements AssetSerializator<CubeTextureData> {

    @Override
    public Asset<CubeTextureData> load(AssetManager manager, AssetKey ckey) {
        if (ckey instanceof CubeTextureAssetKey key) {
            final var image = manager.load(CubeImageData.class, key.image());
            final var type = key.type().type();
            return AssetKt.map(image, new CubeTextureDataFunction(type));
        }
        return null;
    }

    private static final class CubeTextureDataFunction implements Value1Function<CubeImageData, CubeTextureData> {

        private static final long serialVersionUID = 1L;
        private final Texture3DType type;

        public CubeTextureDataFunction(Texture3DType type) {
            this.type = Objects.requireNonNull(type);
        }

        @Override
        public CubeTextureData applyWithDest(CubeImageData value, CubeTextureData dest) {
            if (dest == null)
                return apply(value);
            if (value == null)
                return dest;
            return apply(value);
        }

        @Override
        public CubeTextureData apply(CubeImageData image) {
            return new CubeTextureData(image, type);
        }

    }

}
