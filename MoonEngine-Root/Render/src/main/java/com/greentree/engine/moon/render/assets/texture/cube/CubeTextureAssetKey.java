package com.greentree.engine.moon.render.assets.texture.cube;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.render.texture.Texture3DType;


public record CubeTextureAssetKey(AssetKey image, Texture3DAssetType type) implements AssetKey {
	
	public CubeTextureAssetKey(String name) {
		this(name, new Texture3DType());
	}
	public CubeTextureAssetKey(String name, Texture3DType type) {
		this(new ResourceAssetKey(name), new Texture3DAssetType(type));
	}

}
