package com.greentree.engine.moon.render.assets.texture;

import com.greentree.commons.image.image.ImageData;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.render.texture.Texture2DData;
import com.greentree.engine.moon.render.texture.Texture2DType;

public class Texture2DAssetSerializator implements AssetSerializator<Texture2DData> {

    @Override
    public Asset<Texture2DData> load(AssetLoader.Context manager, AssetKey ckey) {
        if (ckey instanceof Texture2DAssetKey key) {
            final var image = manager.load(ImageData.class, key.image());
            final var type = key.textureType();
            return AssetKt.map(image, new Texture2DDataFunction(type));
        }
        final var image = manager.load(ImageData.class, ckey);
        return AssetKt.map(image, new Texture2DDataFunction());
    }

    private static final class Texture2DDataFunction
            implements Value1Function<ImageData, Texture2DData> {

        private static final long serialVersionUID = 1L;
        private final Texture2DType type;

        public Texture2DDataFunction(Texture2DType type) {
            this.type = type;
        }

        public Texture2DDataFunction() {
            this.type = new Texture2DType();
        }

        @Override
        public Texture2DData apply(ImageData value) {
            return new Texture2DData(value, type);
        }

    }

}
