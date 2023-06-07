package com.greentree.engine.moon.render.assets.image;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.image.Color;
import com.greentree.commons.image.image.ColorImageData;
import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.image.loader.ImageIOImageLoader;
import com.greentree.commons.image.loader.PNGImageData;
import com.greentree.commons.image.loader.TGAImageData;
import com.greentree.commons.util.exception.MultiException;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.AssetKeyType;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.serializator.manager.DefaultAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;

import java.io.IOException;

public class ImageAssetSerializator implements AssetSerializator<ImageData> {

    @Override
    public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
        return manager.canLoad(Resource.class, key) || manager.canLoad(Color.class, key);
    }

    @Override
    public Value<ImageData> load(LoadContext manager, AssetKey key) {
        if (manager.canLoad(Resource.class, key)) {
            final var res = manager.load(Resource.class, key);
            return manager.map(res, new ImageDataAsset());
        }
        if (manager.canLoad(Color.class, key)) {
            final var color = manager.load(Color.class, key);
            return manager.map(color, new ColorTextureAsset());
        }
        return null;
    }

    @Override
    public ImageData loadDefault(DefaultAssetManager manager, AssetKeyType type) {
        return new ColorImageData(manager.loadDefault(Color.class, Color.black));
    }

    private static final class ColorTextureAsset implements Value1Function<Color, ImageData> {

        private static final long serialVersionUID = 1L;

        @Override
        public ImageData apply(Color color) {
            return new ColorImageData(color);
        }

    }

    private static final class ImageDataAsset implements Value1Function<Resource, ImageData> {

        private static final long serialVersionUID = 1L;

        @Override
        public ImageData apply(Resource res) {
            if (res == null)
                return null;
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
