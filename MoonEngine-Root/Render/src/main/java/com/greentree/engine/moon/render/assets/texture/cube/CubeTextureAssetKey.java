package com.greentree.engine.moon.render.assets.texture.cube;

import com.greentree.common.renderer.texture.Texture3DType;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResourceAssetKey;


public record CubeTextureAssetKey(AssetKey image, Texture3DAssetType type) implements AssetKey {
	
	public CubeTextureAssetKey(String name) {
		this(name, new Texture3DType());
	}
	public CubeTextureAssetKey(String name, Texture3DType type) {
		this(new ResourceAssetKey(name), new Texture3DAssetType(type));
	}

}
