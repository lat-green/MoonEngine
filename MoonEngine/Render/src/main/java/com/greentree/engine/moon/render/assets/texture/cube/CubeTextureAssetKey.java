package com.greentree.engine.moon.render.assets.texture.cube;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.render.texture.Texture3DType;

public record CubeTextureAssetKey(AssetKey image, Texture3DType textureType) implements AssetKey {

    public CubeTextureAssetKey(String resource) {
        this(new ResourceAssetKey(resource));
    }

    public CubeTextureAssetKey(AssetKey image) {
        this(image, new Texture3DType());
    }

    public CubeTextureAssetKey(String resource, Texture3DType textureType) {
        this(new ResourceAssetKey(resource), textureType);
    }

}
