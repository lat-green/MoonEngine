package com.greentree.engine.moon.render.assets.image.cube;

import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value6Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey;
import com.greentree.engine.moon.render.texture.CubeImageData;

public class PropertiesCubeImageAssetSerializator implements AssetSerializator<CubeImageData> {

    @Override
    public Asset<CubeImageData> load(AssetManager context, AssetKey ckey) {
        final var posx_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "posx"));
        final var negx_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "negx"));
        final var posy_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "posy"));
        final var negy_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "negy"));
        final var posz_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "posz"));
        final var negz_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "negz"));
        final Asset<ImageData> posx, negx, posy, negy, posz, negz;
        posx = context.load(ImageData.class, posx_res);
        negx = context.load(ImageData.class, negx_res);
        posy = context.load(ImageData.class, posy_res);
        negy = context.load(ImageData.class, negy_res);
        posz = context.load(ImageData.class, posz_res);
        negz = context.load(ImageData.class, negz_res);
        return AssetKt.map(posx, negx, posy, negy, posz, negz, new CubeImageAsset());
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
