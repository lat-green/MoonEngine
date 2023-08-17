package com.greentree.engine.moon.render.assets.image;

import com.greentree.commons.image.Color;
import com.greentree.commons.image.image.ColorImageData;
import com.greentree.commons.image.image.ImageData;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class ColorImageAssetSerializator implements AssetSerializator<ImageData> {

    @Override
    public Asset<ImageData> load(AssetManager manager, AssetKey key) {
        final var color = manager.load(Color.class, key);
        return AssetKt.map(color, new ColorToImage());
    }

    private static final class ColorToImage implements Value1Function<Color, ImageData> {

        private static final long serialVersionUID = 1L;

        @Override
        public ImageData apply(Color color) {
            return new ColorImageData(color);
        }

    }

}
