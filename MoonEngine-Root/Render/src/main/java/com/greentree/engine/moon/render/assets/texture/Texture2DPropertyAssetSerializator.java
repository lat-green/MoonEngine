package com.greentree.engine.moon.render.assets.texture;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.render.material.Property;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.texture.Texture2DData;

public class Texture2DPropertyAssetSerializator implements AssetSerializator<Property> {

    private final RenderLibrary library;

    public Texture2DPropertyAssetSerializator(RenderLibrary library) {
        this.library = library;
    }

    @Override
    public Asset<Property> load(AssetManager manager, AssetKey key) {
        var data = manager.load(Texture2DData.class, key);
        return AssetKt.map(data, x -> library.build(x));
    }

}
