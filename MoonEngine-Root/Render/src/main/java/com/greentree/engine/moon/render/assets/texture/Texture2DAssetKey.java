package com.greentree.engine.moon.render.assets.texture;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.render.texture.data.Texture2DType;


public record Texture2DAssetKey(AssetKey image, Texture2DType textureType) implements AssetKey {
	
	public Texture2DAssetKey(AssetKey image) {
		this(image, new Texture2DType());
	}
	
	public Texture2DAssetKey(String resource) {
		this(new ResourceAssetKey(resource));
	}
	
	public Texture2DAssetKey(String resource, Texture2DType type) {
		this(new ResourceAssetKey(resource), type);
	}
	
	@Override
	public Texture2DAssetType type() {
		return new Texture2DAssetType(textureType);
	}
	
}
