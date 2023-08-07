package com.greentree.engine.moon.render.assets.image.cube;

import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value6Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.render.texture.CubeImageData;

public class CubeImageAssetSerializator implements AssetSerializator<CubeImageData> {

    @Override
    public Asset<CubeImageData> load(AssetManager context, AssetKey ckey) {
        if (ckey instanceof CubeImageAssetKey key) {
            final var posx = context.load(ImageData.class, key.posx());
            final var negx = context.load(ImageData.class, key.negx());
            final var posy = context.load(ImageData.class, key.posy());
            final var negy = context.load(ImageData.class, key.negy());
            final var posz = context.load(ImageData.class, key.posz());
            final var negz = context.load(ImageData.class, key.negz());
            return AssetKt.map(posx, negx, posy, negy, posz, negz, new CubeImageAsset());
        }
        return null;
    }

    private static final class CubeImageAsset implements
            Value6Function<ImageData, ImageData, ImageData, ImageData, ImageData, ImageData, CubeImageData> {

        private static final long serialVersionUID = 1L;

        @Override
        public CubeImageData apply(ImageData posx, ImageData negx, ImageData posy, ImageData negy,
                                   ImageData posz, ImageData negz) {
            final var iter = IteratorUtil.iterable(posx, negx, posy, negy, posz, negz);
            for (var a : iter)
                if (a == null)
                    return null;
                else
                    for (var b : iter)
                        if (a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight())
                            return null;
            return new CubeImageData(posx, negx, posy, negy, posz, negz);
        }

    }

}
