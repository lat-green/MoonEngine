package com.greentree.engine.moon.render.assets.shader;

import java.util.Objects;

import com.greentree.common.renderer.shader.ShaderType;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.key.ResourceAssetKey;


public final record ShaderAssetKey(AssetKey text, ShaderType shaderType) implements AssetKey {
	
	private static final long serialVersionUID = 1L;
	
	public ShaderAssetKey {
		Objects.requireNonNull(text);
		Objects.requireNonNull(shaderType);
	}
	
	@Override
	public AssetKeyType type() {
		return new ShaderAssetType(shaderType);
	}
	
	public ShaderAssetKey(String text, ShaderType type) {
		this(new ResourceAssetKey(text), type);
	}
	
}
