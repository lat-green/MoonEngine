package com.greentree.engine.moon.render.assets.texture;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.render.material.Property;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.texture.CubeTextureData;
import com.greentree.engine.moon.render.texture.Texture2DData;

public class TextureAssetSerializator implements AssetSerializator<Property> {

    private final RenderLibrary library;

    public TextureAssetSerializator(RenderLibrary library) {
        this.library = library;
    }

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        return manager.canLoad(Texture2DData.class, key) || manager.canLoad(CubeTextureData.class, key);
    }

    @Override
    public Asset<Property> load(AssetManager manager, AssetKey key) {
        if (manager.canLoad(Texture2DData.class, key)) {
            var data = manager.load(Texture2DData.class, key);
            return AssetKt.map(data, x -> library.build(x));
        }
        if (manager.canLoad(CubeTextureData.class, key)) {
            var data = manager.load(CubeTextureData.class, key);
            return AssetKt.map(data, x -> library.build(x));
        }
        return null;
    }

}
