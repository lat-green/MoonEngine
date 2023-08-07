package com.greentree.engine.moon.render.assets.image;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.image.loader.ImageIOImageLoader;
import com.greentree.commons.image.loader.PNGImageData;
import com.greentree.commons.image.loader.TGAImageData;
import com.greentree.commons.util.exception.MultiException;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

import java.io.IOException;

public class ResourceImageAssetSerializator implements AssetSerializator<ImageData> {

    @Override
    public Asset<ImageData> load(AssetManager manager, AssetKey key) {
        final var res = manager.load(Resource.class, key);
        return AssetKt.map(res, new ImageDataAsset());
    }

    private static final class ImageDataAsset implements Value1Function<Resource, ImageData> {

        private static final long serialVersionUID = 1L;

        @Override
        public ImageData apply(Resource res) {
            try (final var in = res.open()) {
                return ImageIOImageLoader.IMAGE_DATA_LOADER.loadImage(in);
            } catch (IOException e1) {
                try (final var in = res.open()) {
                    return PNGImageData.IMAGE_DATA_LOADER.loadImage(in);
                } catch (IOException e2) {
                    try (final var in = res.open()) {
                        return TGAImageData.IMAGE_DATA_LOADER.loadImage(in);
                    } catch (IOException e3) {
                        throw new MultiException(e1, e2, e3);
                    }
                }
            }
        }

    }

}
