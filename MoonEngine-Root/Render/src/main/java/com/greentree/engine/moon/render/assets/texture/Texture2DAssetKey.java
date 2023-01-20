package com.greentree.engine.moon.render.assets.texture;

import com.greentree.common.renderer.texture.Texture2DType;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResourceAssetKey;


public record Texture2DAssetKey(AssetKey image, Texture2DType textureType) implements AssetKey {

	public Texture2DAssetKey(String resource, Texture2DType type) {
		this(new ResourceAssetKey(resource), type);
	}

	@Override
	public Texture2DAssetType type() {
		return new Texture2DAssetType(textureType);
	}

}
